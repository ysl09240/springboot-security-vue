package com.sykean.hmhc.req.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sykean.hmhc.req.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户查询model")
public class UserListReq extends PageReq {

    @ApiModelProperty("用户姓名")
    private String realName;

    @ApiModelProperty("警务编号")
    private String jzCode;

    @ApiModelProperty("身份证号码")
    private String cerNo;

    @ApiModelProperty("所属组织")
    private String deptId;

    @ApiModelProperty("角色id")
    private String roleId;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("状态")
    private String state;

    @ApiModelProperty("开始创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startCreateTime;

    @ApiModelProperty("结束创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endCreateTime;
}
