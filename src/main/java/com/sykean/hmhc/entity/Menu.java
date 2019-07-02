package com.sykean.hmhc.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表
 */
@TableName("sys_menu")
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 7322715000636340817L;
    //id
    @TableId(type = IdType.INPUT)
    private String id;

    //名称
    private String label;

    private String code;

    private String path;

    //地址
    private String url;

    //样式class
    private String icon;

    //父级id
    private String parentId;

    //1-菜单，2-按钮
    private Integer type;

    //权限标识
    private String permission;

    private Integer order;


    
    //创建用户id
    private String createUserId;
    
    //创建时间
    private Date createTime;
    
    //修改用户id
    private String updateUserId;
    
    //修改时间
    private Date updateTime;
 

}