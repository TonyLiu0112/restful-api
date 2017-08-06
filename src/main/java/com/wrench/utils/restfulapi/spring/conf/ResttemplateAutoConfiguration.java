package com.wrench.utils.restfulapi.spring.conf;

import com.wrench.utils.restfulapi.RestTemplateWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(ResttemplateProperties.class)
@ConditionalOnClass(RestTemplate.class)
@ConditionalOnProperty(prefix = "resttemplate.warpper", value = "enable", matchIfMissing = true)
public class ResttemplateAutoConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplateWrapper restTemplateWrapper() {
        return new RestTemplateWrapper(restTemplate());
    }
}
