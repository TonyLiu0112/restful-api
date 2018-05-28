package com.wrench.utils.restfulapi.response;

import com.wrench.utils.restfulapi.helper.DefaultErrorMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "业务负载信息")
public class AdditionPayload implements Serializable {

    @ApiModelProperty("业务代码")
    private String code;

    @ApiModelProperty("代码消息")
    private String message;

    public AdditionPayload() {
    }

    public AdditionPayload(DefaultErrorMsg errorMsg) {
        this(errorMsg.getCode(), errorMsg.getMsg());
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
