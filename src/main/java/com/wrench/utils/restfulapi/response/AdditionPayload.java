package com.wrench.utils.restfulapi.response;

import java.io.Serializable;

public class AdditionPayload implements Serializable {

    /**
     * 业务响应码
     */
    private String code;

    private String message;

    public AdditionPayload() {
    }

    public AdditionPayload(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
