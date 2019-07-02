package com.sykean.hmhc.req.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("分页请求req")
public class PageReq implements Serializable {
    private static final long serialVersionUID = 666487620133112654L;

    @ApiModelProperty("每页个数")
    private Integer pageSize;

    @ApiModelProperty("偏移量")
    private Integer pageIndex;

    public PageReq() {
        pageSize = 10;
        pageIndex = 1;
    }



}
