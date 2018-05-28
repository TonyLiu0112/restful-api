package com.wrench.utils.restfulapi.oauth;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "restful.oauth")
public class RestfulOauth2Properties {

    private List<String> ignores;

    public List<String> getIgnores() {
        return ignores;
    }

    public void setIgnores(List<String> ignores) {
        this.ignores = ignores;
    }
}
