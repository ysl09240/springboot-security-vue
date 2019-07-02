package com.sykean.hmhc.res.collect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author litong
 * @description 采集信息详情返回体
 * @date 2019/6/20
 */
@Data
@ApiModel("采集信息详情")
public class CollectDetailRes {
    //1.基础信息
        @ApiModelProperty("id")
        private String id;

        @ApiModelProperty("姓名")
        @JsonProperty("sName")
        private String sName;

        @ApiModelProperty("性别")
        @JsonProperty("sGender")
        private String sGender;

        @ApiModelProperty("国籍")
        @JsonProperty("sCountry")
        private String sCountry;

        @ApiModelProperty("民族")
        @JsonProperty("sNation")
        private String sNation;

        @ApiModelProperty("民族")
        @JsonProperty("sNationName")
        private String sNationName;

        @ApiModelProperty("出生日期")
        @JsonProperty("sBirth")
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
        private Date sBirth;

        @ApiModelProperty("年龄")
        private Integer age;

        @ApiModelProperty("证件类型")
        private String cerType;

        @ApiModelProperty("证件类型名称")
        private String cerTypeName;

        @ApiModelProperty("证件号码")
        private String cerNo;

        @ApiModelProperty("签发机关")
        private String cerAuth;

        @ApiModelProperty("有效期限")
        private String cerValid;

        @ApiModelProperty("户籍地址")
        private String addr1;

        @ApiModelProperty("现住址")
        private String addr2;

        @ApiModelProperty("手机号1")
        private String tel1;

        @ApiModelProperty("手机号2")
        private String tel2;

        @ApiModelProperty("人员安全级别")
        private String secLev;

        @ApiModelProperty("同步标识")
        private String flgSync;

        @ApiModelProperty("身份证照片")
        private String headImage;

    //2.采集信息
        @ApiModelProperty("采集时间")
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createTime;

        @ApiModelProperty("设备类型")
        private String devType;

        @ApiModelProperty("设备类型名称")
        private String devTypeName;

        @ApiModelProperty("采集站点")
        private String stId;

        @ApiModelProperty("采集站点名称")
        private String stName;

        @ApiModelProperty("采集站点经度")
        private BigDecimal longitude;

        @ApiModelProperty("采集站点纬度")
        private BigDecimal latitude;

        @ApiModelProperty("采集站点坐标")
        private String stCoordinate;

        @ApiModelProperty("采集站点类型")
        private String stType;

        @ApiModelProperty("采集站点类型")
        private String stTypeName;

        @ApiModelProperty("采集人")
        private String createUserName;

        @ApiModelProperty("采集原因")
        private String reasonColl;

        @ApiModelProperty("采集原因名称")
        private String reasonCollName;

        @ApiModelProperty("采集原因描述")
        private String reasonCollDesc;

        @ApiModelProperty("身份标签")
        @JsonProperty("sSign")
        private String sSign;

        @ApiModelProperty("强制采集标志")
        private String flgForce;

        @ApiModelProperty("是否为工作对象")
        private String flgWo;

        @ApiModelProperty("临时关注标志")
        private String flgFocus;

        @ApiModelProperty("临时关注备注")
        private String focusDesc;

        @ApiModelProperty("临时关注结束日期")
        private String focusEndDate;
    //3.生物特征
        @ApiModelProperty("虹膜数据信息（左眼）")
        @JsonProperty("irisL")
        private String irisL;

        @ApiModelProperty("虹膜数据信息（右眼）")
        @JsonProperty("irisR")
        private String irisR;

        @ApiModelProperty("左眼虹膜质量")
        @JsonProperty("qL")
        private String qL;

        @ApiModelProperty("右眼虹膜质量")
        @JsonProperty("qR")
        private String qR;

        @ApiModelProperty("人脸照片")
        private String irisFace;
}
