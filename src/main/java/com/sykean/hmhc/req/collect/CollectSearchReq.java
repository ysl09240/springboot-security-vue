package com.sykean.hmhc.req.collect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sykean.hmhc.req.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author litong 采集信息查询请求体
 * @description
 * @date 2019/6/19
 */
@Data
@ApiModel("采集信息详情")
public class CollectSearchReq extends PageReq {

    @ApiModelProperty("人员姓名")
    @JsonProperty("sName")
    private String sName;

    @ApiModelProperty("民族")
    @JsonProperty("sNation")
    private String sNation;

    @ApiModelProperty("证件类型")
    private String cerType;

    @ApiModelProperty("证件号码")
    private String cerNo;

    @ApiModelProperty("户籍地址")
    private String addr1;

    @ApiModelProperty("省Id")
    private String provinceId;

    @ApiModelProperty("省名称")
    private String provinceName;

    @ApiModelProperty("市Id")
    private String cityId;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区Id")
    private String countyId;

    @ApiModelProperty("区名称")
    private String countyName;

    //采集点位（当前系统对应组织）
    @ApiModelProperty("采集点位")
    @JsonProperty("stId")
    private String stId;

    @ApiModelProperty("采集开始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collectStartTime;

    @ApiModelProperty("采集结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collectEndTime;

}
