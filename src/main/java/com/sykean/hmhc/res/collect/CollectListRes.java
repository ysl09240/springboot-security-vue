package com.sykean.hmhc.res.collect;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author litong 采集信息列表返回体
 * @description
 * @date 2019/6/19
 */
@ApiModel("采集信息列表")
@Data
public class CollectListRes {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("人员姓名")
    @JsonProperty("sName")
    private String sName;

    @ApiModelProperty("民族")
    @JsonProperty("sNation")
    private String sNation;

    @ApiModelProperty("民族名称")
    @JsonProperty("sNationName")
    private String sNationName;

    @ApiModelProperty("证件类型")
    private String cerType;

    @ApiModelProperty("证件类型名称")
    private String cerTypeName;

    @ApiModelProperty("证件号码")
    private String cerNo;

    @ApiModelProperty("户籍地址")
    private String addr1;

    @ApiModelProperty("采集时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("采集点位")
    private String stId;

    @ApiModelProperty("采集点位名称")
    private String stName;

    @ApiModelProperty("采集人")
    private String createUserId;

    @ApiModelProperty("采集人姓名")
    private String createUserName;

    @ApiModelProperty("采集原因")
    private String reasonColl;

    @ApiModelProperty("采集原因名称")
    private String reasonCollName;

}
