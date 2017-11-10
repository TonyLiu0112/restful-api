package com.wrench.utils.restfulapi.spring.conf;

import com.wrench.utils.restfulapi.RestOps;
import com.wrench.utils.restfulapi.client.RestTemplateClient;
import com.wrench.utils.restfulapi.client.RestTemplateWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(ResttemplateProperties.class)
@ConditionalOnBean(RestTemplate.class)
@ConditionalOnProperty(prefix = "resttemplate.warpper", value = "enable", matchIfMissing = true)
public class RestTemplateAutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnBean(RestTemplate.class)
//    public RestTemplateClient restTemplateWrapper(RestTemplate restTemplate) {
//        return new RestTemplateClient(restTemplate);
//    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(RestTemplate.class)
    public RestOps restOps(RestTemplate restTemplate) {
        return new RestTemplateWrapper(restTemplate);
    }
}
