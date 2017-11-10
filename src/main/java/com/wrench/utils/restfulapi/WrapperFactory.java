package com.wrench.utils.restfulapi;

import com.wrench.utils.restfulapi.client.RestTemplateClient;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class WrapperFactory {

    public static RestTemplateClient getRestTemplateWrapper() {
        return new RestTemplateClient(new RestTemplate(){{
            getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        }});
    }

}
