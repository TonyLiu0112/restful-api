package com.wrench.utils.restfulapi.oauth;

import jodd.util.StringUtil;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.http.OAuth2ErrorHandler;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * 重写Spring Oauth2 RestTemplate行为
 *
 * @author Tony
 */
public class RestfulOAuth2Resttemplate extends RestTemplate implements OAuth2RestOperations {

    private final OAuth2ProtectedResourceDetails resource;

    private final RestfulOauth2Properties properties;

    private AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(
            Arrays.<AccessTokenProvider>asList(new AuthorizationCodeAccessTokenProvider(),
                    new ImplicitAccessTokenProvider(),
                    new ResourceOwnerPasswordAccessTokenProvider(),
                    new ClientCredentialsAccessTokenProvider()));

    private OAuth2ClientContext context;

    private boolean retryBadAccessTokens = true;

    private OAuth2RequestAuthenticator authenticator = new DefaultOAuth2RequestAuthenticator();

    public RestfulOAuth2Resttemplate(RestfulOauth2Properties properties, OAuth2ProtectedResourceDetails resource) {
        this(properties, resource, new DefaultOAuth2ClientContext());
    }

    public RestfulOAuth2Resttemplate(RestfulOauth2Properties properties, OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        super();
        if (resource == null) {
            throw new IllegalArgumentException("An OAuth2 resource must be supplied.");
        }

        this.resource = resource;
        this.context = context;
        this.properties = properties;
        setErrorHandler(new OAuth2ErrorHandler(resource));
    }

    public void setAuthenticator(OAuth2RequestAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setRetryBadAccessTokens(boolean retryBadAccessTokens) {
        this.retryBadAccessTokens = retryBadAccessTokens;
    }

    @Override
    public void setErrorHandler(ResponseErrorHandler errorHandler) {
        if (!(errorHandler instanceof OAuth2ErrorHandler)) {
            errorHandler = new OAuth2ErrorHandler(errorHandler, resource);
        }
        super.setErrorHandler(errorHandler);
    }

    @Override
    public OAuth2ProtectedResourceDetails getResource() {
        return resource;
    }

    @Override
    protected ClientHttpRequest createRequest(URI uri, HttpMethod method) throws IOException {
        if (isIgnore(uri.getPath())) {
            return super.createRequest(uri, method);
        }

        OAuth2AccessToken accessToken = getAccessToken();

        AuthenticationScheme authenticationScheme = resource.getAuthenticationScheme();
        if (AuthenticationScheme.query.equals(authenticationScheme) || AuthenticationScheme.form.equals(authenticationScheme)) {
            uri = appendQueryParameter(uri, accessToken);
        }

        ClientHttpRequest req = super.createRequest(uri, method);

        if (AuthenticationScheme.header.equals(authenticationScheme)) {
            authenticator.authenticate(resource, getOAuth2ClientContext(), req);
        }
        return req;

    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
                              ResponseExtractor<T> responseExtractor) throws RestClientException {

        if (isIgnore(url.getPath())) {
            return super.doExecute(url, method, requestCallback, responseExtractor);
        }

        OAuth2AccessToken accessToken = context.getAccessToken();
        RuntimeException rethrow;
        try {
            return super.doExecute(url, method, requestCallback, responseExtractor);
        } catch (AccessTokenRequiredException | OAuth2AccessDeniedException e) {
            rethrow = e;
        } catch (InvalidTokenException e) {
            // Don't reveal the token value in case it is logged
            rethrow = new OAuth2AccessDeniedException("Invalid token for client=" + getClientId());
        }
        if (accessToken != null && retryBadAccessTokens) {
            context.setAccessToken(null);
            try {
                return super.doExecute(url, method, requestCallback, responseExtractor);
            } catch (InvalidTokenException e) {
                // Don't reveal the token value in case it is logged
                rethrow = new OAuth2AccessDeniedException("Invalid token for client=" + getClientId());
            }
        }
        throw rethrow;
    }

    /**
     * @return the client id for this resource.
     */
    private String getClientId() {
        return resource.getClientId();
    }

    /**
     * Acquire or renew an access token for the current context if necessary. This method will be called automatically
     * when a request is executed (and the result is cached), but can also be called as a standalone method to
     * pre-populate the token.
     *
     * @return an access token
     */
    public OAuth2AccessToken getAccessToken() throws UserRedirectRequiredException {

        OAuth2AccessToken accessToken = context.getAccessToken();

        if (accessToken == null || accessToken.isExpired()) {
            try {
                accessToken = acquireAccessToken(context);
            } catch (UserRedirectRequiredException e) {
                context.setAccessToken(null); // No point hanging onto it now
                String stateKey = e.getStateKey();
                if (stateKey != null) {
                    Object stateToPreserve = e.getStateToPreserve();
                    if (stateToPreserve == null) {
                        stateToPreserve = "NONE";
                    }
                    context.setPreservedState(stateKey, stateToPreserve);
                }
                throw e;
            }
        }
        return accessToken;
    }

    /**
     * @return the context for this template
     */
    public OAuth2ClientContext getOAuth2ClientContext() {
        return context;
    }

    @SuppressWarnings("WeakerAccess")
    protected OAuth2AccessToken acquireAccessToken(OAuth2ClientContext oauth2Context)
            throws UserRedirectRequiredException {

        AccessTokenRequest accessTokenRequest = oauth2Context.getAccessTokenRequest();
        if (accessTokenRequest == null) {
            throw new AccessTokenRequiredException("No OAuth 2 security context has been established. Unable to access resource '" + this.resource.getId() + "'.", resource);
        }

        String stateKey = accessTokenRequest.getStateKey();
        if (stateKey != null) {
            accessTokenRequest.setPreservedState(oauth2Context.removePreservedState(stateKey));
        }

        OAuth2AccessToken existingToken = oauth2Context.getAccessToken();
        if (existingToken != null) {
            accessTokenRequest.setExistingToken(existingToken);
        }

        OAuth2AccessToken accessToken;
        accessToken = accessTokenProvider.obtainAccessToken(resource, accessTokenRequest);
        if (accessToken == null || accessToken.getValue() == null) {
            throw new IllegalStateException(
                    "Access token provider returned a null access token, which is illegal according to the contract.");
        }
        oauth2Context.setAccessToken(accessToken);
        return accessToken;
    }

    @SuppressWarnings("WeakerAccess")
    protected URI appendQueryParameter(URI uri, OAuth2AccessToken accessToken) {

        try {

            String query = uri.getRawQuery(); // Don't decode anything here
            String queryFragment = resource.getTokenName() + "=" + URLEncoder.encode(accessToken.getValue(), "UTF-8");
            if (query == null) {
                query = queryFragment;
            } else {
                query = query + "&" + queryFragment;
            }

            URI update = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), null, null);
            StringBuilder sb = new StringBuilder(update.toString());
            sb.append("?");
            sb.append(query);
            if (uri.getFragment() != null) {
                sb.append("#");
                sb.append(uri.getFragment());
            }

            return new URI(sb.toString());

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not parse URI", e);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Could not encode URI", e);
        }

    }

    public void setAccessTokenProvider(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    private boolean isIgnore(String path) {
        List<String> uris = properties.getIgnores();
        if (uris == null || uris.size() == 0)
            return false;
        return uris.stream().anyMatch(ignoreUri -> StringUtil.count(path, ignoreUri) > 0);
    }

}
