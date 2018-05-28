package com.wrench.utils.restfulapi.request;

import io.swagger.annotations.ApiModelProperty;

public class PageReq {

    @ApiModelProperty("页码 默认1")
    private Integer pageNum = 1;

    @ApiModelProperty("每页长度 默认10 最大100")
    private Integer pageSize = 10;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize == null ? 100 : pageSize > 100 ? 100 : pageSize;
    }
}
