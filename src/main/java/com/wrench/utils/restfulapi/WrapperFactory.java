package com.wrench.utils.restfulapi;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class WrapperFactory {

    public static RestTemplateWrapper getRestTemplateWrapper() {
        return new RestTemplateWrapper(new RestTemplate(){{
            getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        }});
    }

}
