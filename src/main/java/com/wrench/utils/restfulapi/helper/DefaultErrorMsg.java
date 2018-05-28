package com.wrench.utils.restfulapi.helper;

public enum DefaultErrorMsg {

    DEFAULT_SERVER_ERROR("10500", "服务器繁忙, 请稍后再试!"),
    DEFAULT_SERVER_NOT_IMPLEMENT("10501", "服务处理失败, 请稍后再试!");

    private String code;

    private String msg;

    DefaultErrorMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
