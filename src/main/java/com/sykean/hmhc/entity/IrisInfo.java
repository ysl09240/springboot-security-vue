package com.sykean.hmhc.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author litong
 * @description 人员虹膜信息
 * @date 2019/6/20
 */
@TableName("bus_iris_info")
@Data
public class IrisInfo implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id;
    //虹膜id
    private String irId;
    //人员编号
    private String sId;
    //虹膜数据信息（左眼）
    private String irisL;
    //虹膜数据信息（右眼）
    private String irisR;
    //手机号1
    private String tel1;
    //手机号2
    private String tel2;
    //旧手机号1
    private String tel1Old;
    //旧手机号2
    private String tel2Old;
    //人员安全级别
    private String secLev;
    //证件不一致标志
    private String flgDiff;
    //人脸图像名称
    private String faceName;
    //采集时间
    private String collTime;
    //设备类型
    private String devType;
    //用户id
    private String userid;
    //操作人
    private String opTeller;
    //操作时间
    private String opTime;
    //离线采集标志
    private String flgOffline;
    //采集原因（人员分类）
    private String reasonColl;
    //采集原因描述
    private String reasonCollDesc;
    //采集编号
    private String collNum;
    //采集人
    private String collName;
    //强制采集标志
    private String flgForce;
    //区域编码
    private String areaCode;
    //无证标志
    private String flgCer;
    //眼睛采集识别标志
    private String flgEye;
    //录入标志
    private String flgTypein;
    //人脸图像标志
    private String flgFace;
    //人脸图像相对全路径
    private String pathFace;
    //证件相对全路径名
    private String pathCer;
    //快速采集识别标志
    private String flgQuick;
    //是否为工作对象
    private String flgWo;
    //警综更新时间
    private String jzupTime;
    //警综耗时
    private String timeJz;
    //采集点
    private String stId;
    //同步标识
    private String flgSync;
    //数据上报标识
    private String flgUpload;
    //虹膜照片数据信息
    private String irisFace;
    //左眼虹膜模板数据信息
    private byte[] irisTemplateL;
    //右眼虹膜模板数据信息
    private byte[] irisTemplateR;
    //创建人
    private String createUserId;
    //创建时间
    private Date createTime;
    //修改人
    private String updateUserId;
    //修改时间
    private Date updateTime;
    //左眼质量
    private String qL;
    //右眼质量
    private String qR;

}
