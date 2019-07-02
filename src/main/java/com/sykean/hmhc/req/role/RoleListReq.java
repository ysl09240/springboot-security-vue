package com.sykean.hmhc.req.role;

import io.swagger.annotations.ApiModel;

import java.util.Date;

import com.sykean.hmhc.res.role.RoleListRes;

import lombok.Data;
@Data
@ApiModel("角色列表model")
public class RoleListReq {
	
	
    private String id;
    //名称
    private String name;

    //角色标识
    private String code;

    //描述
    private String descs;
    
    //创建用户id
    private String createUserId;
    
    //创建时间
    private Date createTime;
    
    //修改用户id
    private String updateUserId;
    
    //修改时间
    private Date updateTime;

}
