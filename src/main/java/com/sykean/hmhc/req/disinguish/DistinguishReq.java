package com.sykean.hmhc.req.disinguish;

import com.sykean.hmhc.common.PageRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by yangsonglin@sykean.com
 * Date 2019/6/24 10:54
 * Description
 */
@Data
public class DistinguishReq  {

    private Integer pageIndex;

    private Integer pageSize;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("民族")
    private String nation;

    @ApiModelProperty("证件类型")
    private String cerType;

    @ApiModelProperty("证件编号")
    private String cerNo;

    @ApiModelProperty("户籍地址")
    private String residenceAddr;

    @ApiModelProperty("识别点位")
    private String opAddr;

    @ApiModelProperty("开始识别时间")
    private String startTime;

    @ApiModelProperty("结束识别时间")
    private String endTime;

    @ApiModelProperty("省Id")
    private String provinceId;

    @ApiModelProperty("市Id")
    private String cityId;

    @ApiModelProperty("区Id")
    private String countyId;

    @ApiModelProperty("左眼虹膜")
    private String irisL;

    @ApiModelProperty("右眼虹膜")
    private String irisR;

    @ApiModelProperty("虹膜id")
    private String irId;

    @ApiModelProperty("采集识别码")
    private String ciId;

    @ApiModelProperty("识别对比的用户id")
    private String userid;

    private String flgEye;

    private String devType;

    private String opTeller;

    private String areaCode;




}
