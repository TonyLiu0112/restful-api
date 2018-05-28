package com.wrench.utils.restfulapi.response;

import org.springframework.http.HttpMethod;

public class LinkOps {

    private HttpMethod method;

    private String uri;

    private String desc;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
