package com.sykean.hmhc.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("修改用户状态req")
public class UpdateUserStateReq {

    @ApiModelProperty("用户id")
    @NotBlank(message = "用户id不能为空")
    private String id;

    @ApiModelProperty("(0:停用 1:启用)")
    @NotBlank(message = "用户状态不能为空")
    private String state;
}
