package com.wrench.utils.restfulapi.client;

import com.wrench.utils.restfulapi.RestOps;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(RestTemplateProperties.class)
@ConditionalOnProperty(prefix = "restful.client", value = "enable", matchIfMissing = true)
public class RestfulClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Order(100)
    public RestOps restOps(RestTemplate restTemplate) {
        return new RestTemplateWrapper(restTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    @LoadBalanced
    @Order(101)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
