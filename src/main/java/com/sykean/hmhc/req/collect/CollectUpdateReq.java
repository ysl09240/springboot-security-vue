package com.sykean.hmhc.req.collect;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author litong
 * @description 采集信息修改请求提
 * @date 2019/6/26
 */
@Data
@ApiModel("采集信息修改请求提")
public class CollectUpdateReq {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("手机号1")
    private String tel1;

    @ApiModelProperty("手机号2")
    private String tel2;

    @ApiModelProperty("现住址")
    @Length(max = 100, message = "现住址最大长度为100")
    @NotBlank(message = "现住址不能为空")
    private String addr2;

    @ApiModelProperty("采集原因")
    @NotBlank(message = "采集原因不能为空")
    private String reasonColl;

    @ApiModelProperty("采集原因描述")
    @NotBlank(message = "采集原因描述不能为空")
    private String reasonCollDesc;

    @ApiModelProperty("身份标签")
    @Length(max = 20, message = "身份标签最大长度为20")
    @NotBlank(message = "身份标签不能为空")
    @JsonProperty("sSign")
    private String sSign;

    @ApiModelProperty("强制采集标志")
    @NotBlank(message = "强制采集不能为空")
    private String flgForce;

    @ApiModelProperty("是否为工作对象")
    @NotBlank(message = "工作对象不能为空")
    private String flgWo;

    @ApiModelProperty("临时关注标志")
    @NotBlank(message = "临时关注不能为空")
    private String flgFocus;

    @ApiModelProperty("临时关注备注")
    private String focusDesc;

    @ApiModelProperty("临时关注结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date focusEndDate;
}
