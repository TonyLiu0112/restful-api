package com.wrench.utils.restfulapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestfulResponse<T> {

    /**
     * http响应码
     */
    private HttpStatus status;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应消息
     */
    private String message;

    private ErrorResponse errData;

    public RestfulResponse() {
        this(null);
    }

    RestfulResponse(T data) {
        this.data = data;
    }

    RestfulResponse(T data, ErrorResponse errData) {
        this.data = data;
        this.errData = errData;
    }

    RestfulResponse(T data, ErrorResponse errData, String message) {
        this.data = data;
        this.errData = errData;
        this.message = message;
    }

    ResponseEntity<RestfulResponse<T>> send(HttpStatus status) {
        this.status = status;
        if (this.message == null || "".equals(this.message.trim()))
            this.message = status.getReasonPhrase();
        return new ResponseEntity<>(this, status);
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse getErrData() {
        return errData;
    }

    public void setErrData(ErrorResponse errData) {
        this.errData = errData;
    }
}
