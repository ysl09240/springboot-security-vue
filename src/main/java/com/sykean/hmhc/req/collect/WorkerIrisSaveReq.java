package com.sykean.hmhc.req.collect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author pu.zhao
 * @description 采集信息请求体
 * @date 2019/6/22
 */
@Data
@ApiModel("采集信息请求体")
public class WorkerIrisSaveReq {
    //1.基础信息
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

        @ApiModelProperty("出生日期")
        private String birthDate;

        @ApiModelProperty("年龄")
        private Integer age;

        @ApiModelProperty("证件类型")
        private String cerType;

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
        
        @ApiModelProperty("绑定标志")
        private String flgBind;
        
        @ApiModelProperty("身份证头像")
        private String headImage;


    //2.采集信息
        @ApiModelProperty("采集原因")
        private String reasonColl;

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
        private String irisL;

        @ApiModelProperty("虹膜数据信息（右眼）")
        private String irisR;

        @ApiModelProperty("左眼虹膜质量")
        @JsonProperty("qL")
        private String qL;

        @ApiModelProperty("右眼虹膜质量")
        @JsonProperty("qR")
        private String qR;

        @ApiModelProperty("人脸照片")
        private String irisFace;
        
        @ApiModelProperty("左眼虹膜模板数据信息")
        private String templateL;
        
        @ApiModelProperty("右眼虹膜模板数据信息")
        private String templateR;
        
        @ApiModelProperty("虹膜模板类型")
        private int  irisType;
}
