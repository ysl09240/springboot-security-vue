package com.sykean.hmhc.res.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户列表展示model")
public class UserListRes {
    private String id;

    @ApiModelProperty("用户姓名")
    private String realName;

    @ApiModelProperty("警务编号")
    private String jzCode;

    @ApiModelProperty("身份证号码")
    private String cerNo;

    @ApiModelProperty("组织id")
    private String deptId;

    @ApiModelProperty("部门名称")
    private String deptLabel;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("角色名称")
    private String roleLabel;

    @ApiModelProperty("状态值")
    private String state;

    @ApiModelProperty("状态值")
    private String stateLabel;
}
