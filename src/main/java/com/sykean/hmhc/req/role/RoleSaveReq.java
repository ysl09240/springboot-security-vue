package com.sykean.hmhc.req.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

@Data
@ApiModel("角色添加model")
public class RoleSaveReq {
	@ApiModelProperty("角色名")
    @Length(max = 10, message = "角色名最大长度为20")
    @NotBlank(message = "角色名不能为空")
    private String name;

    @ApiModelProperty("权限id")
    @NotBlank(message = "角色权限不能为空")
    private String menuIds;

    @ApiModelProperty("备注")
    private String descs;


}
