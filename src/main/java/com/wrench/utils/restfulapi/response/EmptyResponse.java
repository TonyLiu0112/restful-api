package com.wrench.utils.restfulapi.response;

import java.io.Serializable;

public class EmptyResponse implements Serializable {

    private String emptyKey;

    public String getEmptyKey() {
        return emptyKey;
    }

    public void setEmptyKey(String emptyKey) {
        this.emptyKey = emptyKey;
    }
}
