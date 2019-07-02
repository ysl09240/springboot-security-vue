package com.sykean.hmhc.res.iris;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageInfo;
import com.sykean.hmhc.entity.BusIrisRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yangsonglin@sykean.com
 * Date 2019/6/24 13:52
 * Description
 */
@Data
public class DistinguishVo implements Serializable {

    private static final long serialVersionUID = -5161401561505709654L;
    //人员信息id
    private String workInfoId;
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date sBirth;

    private int age;
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
    //身份证头像
    private String headImage;
    //身份标标签
    private String sSign;

    //虹膜信息
    private String irisInfoId;
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
    //虹膜模板数据信息
    private String irisTemplate;
    //左眼质量
    private String qL;
    //右眼质量
    private String qR;

    private String type;

    private String sNationName;

    private String cerTypeName;

    private String typeName;

    private String codeName;

    private String devTypeName;
    PageInfo<BusIrisRecordVo> busIrisRecords;


}
