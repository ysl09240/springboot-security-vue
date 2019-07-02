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
@ApiModel("虹膜特征提取")
public class IriTiquReq {

    private String url;

    private String data;

   
}
