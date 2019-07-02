package com.sykean.hmhc.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 角色表
 */
@TableName("sys_role")
@Data
public class Role {

    //id
    @TableId(type = IdType.INPUT)
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
