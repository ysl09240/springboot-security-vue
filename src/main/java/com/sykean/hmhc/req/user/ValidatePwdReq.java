package com.sykean.hmhc.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("校验密码model")
public class ValidatePwdReq {

    @ApiModelProperty("待校验的密码")
    @NotBlank(message = "原密码不能为空")
    @Length(max = 20, message = "密码长度不能大于20位")
    private String password;
}
