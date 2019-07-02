package com.sykean.hmhc.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * 用户角色关联表
 */
@TableName("sys_user_role")
@Data
public class UserRole {

    //id
    @TableId(type = IdType.INPUT)
    private String id;

    //用户id
    private String userId;

    //角色id
    private String roleId;

    public UserRole() {
    }

    public UserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
