package com.sykean.hmhc.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@TableName("sys_user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -9136425916995446053L;
    //id
    @TableId(type = IdType.INPUT)
    private String id;

    //用户名
    private String username;

    //密码
    private String password;

    //姓名
    private String realName;

    //联系电话
    private String phone;

    //组织id
    private String deptId;

    //创建人id
    private String createUserId;

    //创建时间
    private Date createTime;

    //修改人id
    private String updateUserId;

    //修改日期
    private Date updateTime;

    //身份证号码
    private String cerNo;

    //警务编号
    private String jzCode;

    //(0:停用 1:在用)
    private String state;

    //身份证人像面
    private String cerFPath;

    //身份证国徽面
    private String cerBPath;

    //警务证人像面
    private String jwFPath;

    //警务证国徽面
    private String jwBPath;

    //性别(0:男 1:女)
    private String gender;

}
