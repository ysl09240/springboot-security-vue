package com.sykean.hmhc.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel("用户修改model")
public class UserUpdateReq {

    private String id;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("性别")
    @NotBlank(message = "性别不能为空")
    private String gender;

    @ApiModelProperty("用户姓名")
    @Length(max = 20, message = "用户姓名最大长度为20")
    @NotBlank(message = "用户姓名不能为空")
    private String realName;

    @ApiModelProperty("联系电话")
    @Length(max = 11, message = "联系电话最大长度为11")
    private String phone;

    @ApiModelProperty("组织id")
    @NotBlank(message = "组织不能为空")
    private String deptId;

    @ApiModelProperty("身份证号码")
    @Length(max = 18, message = "身份证号码最大长度为18")
    @NotBlank(message = "身份证号码不能为空")
    private String cerNo;

    @ApiModelProperty("警务编号")
    @NotBlank(message = "警务编号不能为空")
    @Length(max = 10, message = "警务编号最大长度为10")
    private String jzCode;

    @ApiModelProperty("(0:停用 1:启用)")
    @NotBlank(message = "用户状态不能为空")
    private String state;

    @ApiModelProperty("身份证人像面")
    @NotBlank(message = "身份证人像面")
    private String cerFPath;

    @ApiModelProperty("身份证国徽面")
    @NotBlank(message = "身份证国徽面")
    private String cerBPath;

    @ApiModelProperty("警务证人像面")
    @NotBlank(message = "警务证人像面")
    private String jwFPath;

    @ApiModelProperty("警务证国徽面")
    @NotBlank(message = "警务证国徽面")
    private String jwBPath;

    @ApiModelProperty("角色id")
    private String roleId;
}
