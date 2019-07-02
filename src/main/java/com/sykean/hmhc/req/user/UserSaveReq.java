package com.sykean.hmhc.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel("用户添加model")
public class UserSaveReq {

    @ApiModelProperty("用户名")
    @Length(min = 6, max = 20, message = "用户名长度为6-20")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("性别")
    @NotBlank(message = "性别不能为空")
    private String gender;

    @ApiModelProperty("密码")
    @Length(min = 6, max = 20, message = "密码长度为6-20位")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("用户姓名")
    @Length(max = 20, message = "用户姓名最大长度为20")
    @NotBlank(message = "用户姓名不能为空")
    private String realName;

    @ApiModelProperty("联系电话")
    @Pattern(regexp = "^$|([0-9]{3,4}-[0-9]{7,8}|[0-9]{7,8}|[1][3,4,5,7,8,9][0-9]{9})$", message = "联系电话格式不正确")
    @Length(max = 11, message = "联系电话最大长度为11")
    private String phone;

    @ApiModelProperty("组织id")
    @NotBlank(message = "组织不能为空")
    private String deptId;

    @ApiModelProperty("身份证号码")
    @Pattern(regexp = "^[1-9][0-7]\\d{4}((19\\d{2}(0[13-9]|1[012])(0[1-9]|[12]\\d|30))|(19\\d{2}(0[13578]|1[02])31)|(19\\d{2}02(0[1-9]|1\\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\\d{3}(\\d|X|x)?$", message = "身份证号码不正确")
    @Length(max = 18, message = "身份证号码最大长度为18")
    @NotBlank(message = "身份证号码不能为空")
    private String cerNo;

    @ApiModelProperty("警务编号")
    @NotBlank(message = "警务编号不能为空")
    @Pattern(regexp = "^[0-9]{0,10}$", message = "警务编号格式不正确")
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
