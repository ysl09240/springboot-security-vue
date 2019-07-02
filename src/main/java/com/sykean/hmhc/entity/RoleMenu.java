package com.sykean.hmhc.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 角色菜单关联表
 */
@TableName("sys_role_menu")
@Data
public class RoleMenu {

    //id
    @TableId(type = IdType.INPUT)
    private String id;

    //角色id
    private String roleId;

    //菜单id
    private String menuId;
    
  //创建用户id
    private String createUserId;
    
    //创建时间
    private Date createTime;
    
    //修改用户id
    private String updateUserId;
    
    //修改时间
    private Date updateTime;
    
    public RoleMenu() {
    }

    public RoleMenu(String id,String roleId, String menuId) {
        this.id=id;
    	this.roleId = roleId;
        this.menuId = menuId;
    }
}
