package com.sykean.hmhc.common;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * Created by lee on 02/09/2017.
 */
public enum ResponseCode {
    /**
     * 系统相关
     */
    SUCCESS(0, "请求成功"),
    ERROR(-1, "请求失败"),
    UN_KNOW(-98, "未知错误"),
    TOEKN_INVALID(104, "token不存在或者无效"),
    LOGIN_FAILED(206, "用户不存在或密码错误"),
    LOGOUT_SUCCESS(207, "登出成功"),
    ACCESS_DENIED(208, "权限不足"),
    USER_PASSWORD(209, "原密码错误"),
    USER_RT_PASSWORD(210,"新密码与原密码不能相同"),
    RT_PASSWORD(211,"原密码不能为空"),
    NEW_PASSWORD(212,"新密码不能为空"),
    USER_STOP(213, "用户已停用，无法登录"),

    /**
     * 计划相关
     */
    PLAN_NOT_EXISTS(309, "计划不存在"),
    EXCEL_DATA_IS_EMPTY(301,"Excel导入数据为空"),
    EXCEL_DATA_IS_NONSTANDARD(302,"Excel导入数据不规范"),
    
    DEPT_SUBSET_EXIST(400,"此机构下有子机构，请先移除"),
    DEPT_LINKAGE_UNFINISHED_PLAN(401,"该部门关联未完成计划"),
    DEPT_NOT_EXISTS(402,"该部门不存在"),
    DEPT_RELATION_USERS_EXIST(403,"此机构下有用户，请先移除"),
    
    /**
     * 用户相关
     */
    SUPER_ADMIN_NOT_ALLOW_DELETE(501,"超级管理员不允许删除"),

    USER_NOT_EXISTS(502,"用户不存在"),
 
    FILE_INVALID(503, "文件格式错误"),
    EXCEL_IMPORT_ERROR(504, "excel导入失败"),

    USER_PWD_ERROR(505, "用户密码失败"),
    ROLE_NOT_EXISTS(506,"角色不存在"),
    ROLE_PER_EXISTS(507,"已经存在相同权限的角色"),



    
    /**
     * 采集信息相关
     */
    WORKER_NOT_EXISTS(601, "人员已存在"),
    IRIS_EXISTS(602, "虹膜库已存在"),
    INTERFACE_ERROR(603, "接口调用错误"),
    RESULT_ERROR(604, "接口返回失败"),
    IRISSERVER_ERROR(605, "虹膜特征提取失败");
    
    

    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @JsonValue
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Serializable getValue() {
        return this.code;
    }
}