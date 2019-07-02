package com.sykean.hmhc.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author litong
 * @description 人员信息
 * @date 2019/6/19
 */
@TableName("bus_worker_info")
@Data
public class WorkerInfo implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id;
    //人员编号
    private String sId;
    //虹膜信息id
    private String irId;
    //性别
    private String sGender;
    //姓名
    private String sName;
    //国籍
    private String sCountry;
    //民族
    private String sNation;
    //出生日期
    private Date sBirth;
    //证件类型
    private String cerType;
    //证件号码
    private String cerNo;
    //签发机关
    private String cerAuth;
    //有效期限
    private String cerValid;
    //户籍住址_行政区划代码
    private String addr1Code;
    //户籍住址
    private String addr1;
    //现住址_行政区划代码
    private String addr2Code;
    //现住址
    private String addr2;
    //绑定标志
    private String flgBind;
    //临时关注标志
    private String flgFocus;
    //临时关注结束日期
    private String focusEndDate;
    //临时关注备注
    private String focusDesc;
    //核查状态
    private String checkState;
    //创建人
    private String createUserId;
    //创建时间
    private Date createTime;
    //修改人
    private String updateUserId;
    //修改时间
    private Date updateTime;
    //身份证头像
    private String headImage;
    //身份标标签
    private String sSign;




}
