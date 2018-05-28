package com.wrench.utils.restfulapi.client;

import com.alibaba.fastjson.JSON;
import com.wrench.utils.restfulapi.RestOps;
import com.wrench.utils.restfulapi.response.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * {@link RestTemplate} Wrapper.
 * <p>
 *
 * @author Tony
 */
public class RestTemplateWrapper implements RestOps {

    private Logger logger = LoggerFactory.getLogger(RestTemplateWrapper.class);
    private final RestTemplate restTemplate;

    public RestTemplateWrapper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static class Configuration {
        static HttpHeaders getHeader() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(new ArrayList<MediaType>() {{
                add(MediaType.APPLICATION_JSON);
            }});
            return headers;
        }
    }

    @Override
    public <T> RestResponse<T> get(String uri, ParameterizedTypeReference<RestResponse<T>> responseType) throws Exception {
        try {
            HttpEntity<Object> reqEntity = new HttpEntity<>(null, Configuration.getHeader());
            ResponseEntity<RestResponse<T>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, responseType);
            return responseEntity.getBody();
        } catch (Exception e) {
            return errorHandler(e);
        }
    }

    @Override
    public RestResponse post(String uri, Object requestEntity) throws Exception {
        return this.post(uri, requestEntity, new ParameterizedTypeReference<RestResponse<Object>>() {
        });
    }

    @Override
    public <T> RestResponse<T> post(String uri, Object requestEntity, ParameterizedTypeReference<RestResponse<T>> responseType) throws Exception {
        try {
            HttpEntity<Object> reqEntity = new HttpEntity<>(requestEntity, Configuration.getHeader());
            ResponseEntity<RestResponse<T>> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, responseType);
            return responseEntity.getBody();
        } catch (Exception e) {
            return errorHandler(e);
        }
    }

    @Override
    public RestResponse put(String uri, Object requestEntity) throws Exception {
        return this.put(uri, requestEntity, new ParameterizedTypeReference<RestResponse<Object>>() {
        });
    }

    @Override
    public <T> RestResponse<T> put(String uri, Object requestEntity, ParameterizedTypeReference<RestResponse<T>> responseType) throws Exception {
        try {
            HttpEntity<Object> reqEntity = new HttpEntity<>(requestEntity, Configuration.getHeader());
            ResponseEntity<RestResponse<T>> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, reqEntity, responseType);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            return errorHandler(e);
        }
    }

    @Override
    public RestResponse delete(String uri, Object requestEntity) throws Exception {
        try {
            HttpEntity<Object> reqEntity = new HttpEntity<>(requestEntity, Configuration.getHeader());
            ResponseEntity responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE, reqEntity, Object.class);
            RestResponse restResponse = new RestResponse();
            restResponse.setStatus(responseEntity.getStatusCode());
            restResponse.setMessage(restResponse.getStatus().getReasonPhrase());
            return restResponse;
        } catch (RestClientException e) {
            return errorHandler(e);
        }
    }

    private RestResponse errorHandler(Exception ex) {
        if (ex instanceof HttpStatusCodeException) {
            HttpStatusCodeException e = (HttpStatusCodeException) ex;
            try {
                RestResponse restResponse = JSON.parseObject(e.getResponseBodyAsString(), RestResponse.class);
                if (restResponse == null || restResponse.getStatus() == null)
                    throw e;
                return restResponse;
            } catch (Exception e1) {
                logger.error("调用服务异常.", e1);
                RestResponse errorRes = new RestResponse();
                errorRes.setStatus(e.getStatusCode());
                errorRes.setMessage(e.getMessage());
                return errorRes;
            }
        } else {
            logger.error("调用服务异常.", ex);
            RestResponse errorRes = new RestResponse();
            errorRes.setStatus(INTERNAL_SERVER_ERROR);
            errorRes.setMessage(INTERNAL_SERVER_ERROR.getReasonPhrase());
            return errorRes;
        }
    }
}
