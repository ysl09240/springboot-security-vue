package com.sykean.hmhc.res.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色列表model")
public class RoleListRes  {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

}
