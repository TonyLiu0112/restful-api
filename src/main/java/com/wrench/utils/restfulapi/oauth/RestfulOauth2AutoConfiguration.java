package com.wrench.utils.restfulapi.oauth;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(RestfulOauth2Properties.class)
@ConditionalOnProperty(prefix = "restful.oauth", value = "enable")
@AutoConfigureOrder(0)
public class RestfulOauth2AutoConfiguration {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnMissingBean
    @LoadBalanced
    public RestTemplate restfulOauth2RestTemplate(RestfulOauth2Properties properties, OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {
        return new RestfulOAuth2Resttemplate(properties, details, oauth2ClientContext);
    }

}
