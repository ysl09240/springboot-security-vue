package com.sykean.hmhc.req.role;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色修改model")
public class RoleUpdateReq {
	private String id;

	@ApiModelProperty("角色名")
    @Length(max = 10, message = "角色名最大长度为10")
    @NotBlank(message = "角色名不能为空")
    private String name;

    @ApiModelProperty("权限id")
    @NotBlank(message = "角色权限不能为空")
    private String menuIds;

    @ApiModelProperty("备注")
    private String descs;
    
    
}
