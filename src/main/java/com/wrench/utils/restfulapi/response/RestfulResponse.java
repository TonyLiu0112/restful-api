package com.wrench.utils.restfulapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestfulResponse {

    private HttpStatus status;
    private Object data;
    private String error;

    public RestfulResponse() {
        this(null);
    }

    public RestfulResponse(Object data) {
        this.data = data;
        this.error = null;
    }

    public ResponseEntity<RestfulResponse> send(HttpStatus status) {
        this.status = status;
        return new ResponseEntity<>(this, status);
    }

    public ResponseEntity<RestfulResponse> send(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
        return new ResponseEntity<>(this, status);
    }

    public <R> R getData() {
        return (R) data;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setError(String error) {
        this.error = error;
    }

}
