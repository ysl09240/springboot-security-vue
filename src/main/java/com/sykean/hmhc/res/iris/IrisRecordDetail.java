package com.sykean.hmhc.res.iris;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: 魏鑫辉
 * @Date: 2019/6/21 14:57
 * @Description:
 */
@Data
public class IrisRecordDetail {

    @TableId
    private String id;
    //姓名
    private String name;
    //国籍
    private String country;
    //性别
    private String gender;
    //民族
    private String nation;
    //年龄
    private int age;
    //出生日期
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;
    //证件类型
    private String cerType;
    //证件号码
    private String cerNo;
    //有效期限
    private String cerValid;
    //签发机关
    private String cerAuth;
    //户籍地址
    private String residenceAddr;
    //居住地址
    private String addr2;
    //电话号码1
    private String tel1;
    //电话号码2
    private String tell2;
    //采集时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collTime;
    //采集站点
    private String collName;
    //采集点坐标X
    private String collCoordinateX;
    //采集点坐标Y
    private String collCoordinateY;
    //站点类型
    private String siteType;
    //采集人
    private String collWorker;
    //采集设备
    private String devType;
    //采集原因
    private String collReason;
    //采集原因描述
    private String collReasonDesc;
    //身份标签
    private String identityLabel;
    //是否强制采集
    private String flgForce;
    //工作对象
    private String flgWorker;
    //临时关注
    private String flgFocus;
    //临时关注备注
    private String flgFocusDesc;
    //临时关注结束日期
    private String endFocus;
    //采集耗时
    private String collConsumption;
    //左眼虹膜
    private String irisLeft;
    //右眼虹膜
    private String irisRight;
    //左眼虹膜质量
    private String irisLeftQuality;
    //右眼虹膜质量
    private String irisRightQuality;
    //人脸照片
    private String facePath;
    //人员安全级别
    private String securityLevel;
    //证件照片
    private String headImage;
    //识别站点
    private String dzmc;
    //识别时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date opTime;
    //操作人
    private String opTeller;
    //报警原因
    private String alarmReason;
    //报警原因描述
    private String alarmReasonDesc;
    //警综耗时
    private String jzTime;
    //识别设备
    private String distinguishDevType;
    //同步标志
    private String flgSync;
    //采集记录集合
    private List<IrisRecordInfo> recordList;

    //虹膜Id
    private String irId;

}
