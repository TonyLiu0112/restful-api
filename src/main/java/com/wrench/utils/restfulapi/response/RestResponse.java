package com.wrench.utils.restfulapi.response;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Set;

@ApiModel("统一接口响应对象")
public class RestResponse<T> implements Serializable {

    @ApiModelProperty(required = true, name = "响应状态(HTTP状态码)")
    private HttpStatus status;

    @ApiModelProperty("业务数据对象")
    private T data;

    @ApiModelProperty("业务数据分页信息")
    private Page page;

    @ApiModelProperty("响应消息")
    private String message;

    @ApiModelProperty("业务附加信息(业务/错误代码)")
    private AdditionPayload addition;

    private Set<LinkOps> links;

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

    public T getData() {
        return data;
    }

    public PageInfo<T> buildPageInfo() {
        Page page = this.getPage();
        com.github.pagehelper.Page<T> gitPage = new com.github.pagehelper.Page<>();
        BeanUtils.copyProperties(page, gitPage);
        return new PageInfo<>(gitPage);
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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Set<LinkOps> getLinks() {
        return links;
    }

    public void setLinks(Set<LinkOps> links) {
        this.links = links;
    }
}
