package com.sykean.hmhc.res.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("用户编辑model")
public class UserEditRes {
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("性别")
    private String genderLabel;

    @ApiModelProperty("组织id")
    private String deptId;

    @ApiModelProperty("组织名称")
    private String deptLabel;

    @ApiModelProperty("身份证号码")
    private String cerNo;

    @ApiModelProperty("警务编号")
    private String jzCode;

    @ApiModelProperty("(0:停用 1:启用)")
    private String state;

    @ApiModelProperty("(0:停用 1:启用)")
    private String stateLabel;

    @ApiModelProperty("身份证人像面")
    @NotBlank(message = "身份证人像面")
    private String cerFPath;

    @ApiModelProperty("身份证国徽面")
    private String cerBPath;

    @ApiModelProperty("警务证人像面")
    private String jwFPath;

    @ApiModelProperty("警务证国徽面")
    private String jwBPath;

    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleName;
}
