package com.sykean.hmhc.req.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import lombok.Data;

import com.sykean.hmhc.req.common.PageReq;
@Data
@ApiModel("角色列表model")
public class RoleReq  extends PageReq{
	@ApiModelProperty("名称")
    private String name;
}
