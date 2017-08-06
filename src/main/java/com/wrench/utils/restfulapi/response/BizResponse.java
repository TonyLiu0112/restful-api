package com.wrench.utils.restfulapi.response;

public class BizResponse {

    /**
     * 业务响应码
     */
    private String code;

    /**
     * 业务数据
     */
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
