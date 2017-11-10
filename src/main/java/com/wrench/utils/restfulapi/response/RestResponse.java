package com.wrench.utils.restfulapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

public class RestResponse<T> implements Serializable {

    /**
     * http响应码
     */
    private HttpStatus status;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应数据集
     */
    private List<T> datas;

    /**
     * 分页信息
     */
    private Page page;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 附加信息
     * 用户描述业务/错误代码
     */
    private AdditionPayload addition;

    public RestResponse() {
        this(null);
    }

    RestResponse(T data) {
        this.data = data;
    }

    RestResponse(T data, AdditionPayload addition) {
        this.data = data;
        this.addition = addition;
    }

    public RestResponse(T data, AdditionPayload addition, String message) {
        this.data = data;
        this.addition = addition;
        this.message = message;
    }

    public ResponseEntity<RestResponse> send(HttpStatus status) {
        this.status = status;
        if (this.message == null || "".equals(this.message.trim()))
            this.message = status.getReasonPhrase();
        return new ResponseEntity<>(this, status);
    }

    public Object getData() {
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

    public AdditionPayload getAddition() {
        return addition;
    }

    public void setAddition(AdditionPayload addition) {
        this.addition = addition;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
