package com.sykean.hmhc.res.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import lombok.Data;

@Data
@ApiModel("菜单信息")
public class MenuVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("菜单名称")
    private String label;

    @ApiModelProperty("资源路径")
    private String url;

    @ApiModelProperty("code")
    private String code;

    @ApiModelProperty("前端路由")
    private String path;

    @ApiModelProperty("图标样式")
    private String icon;

    @ApiModelProperty("父级id")
    private String parentId;

    private Integer type;
    
    //权限标识
    private String permission;

    private Integer order;
    
 // 子菜单
    private List<MenuVO> childMenus;
   



}
