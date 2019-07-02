package com.sykean.hmhc.res.login;

import com.sykean.hmhc.common.MenuTree;
import com.sykean.hmhc.res.menu.MenuVO;
import com.sykean.hmhc.security.Token;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel("用户信息")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -1803945858905279257L;
    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("警务编号")
    private String jzCode;

    @ApiModelProperty("组织id")
    private String deptId;

    @ApiModelProperty("组织名称")
    private String deptName;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户菜单")
    private List<MenuTree<MenuVO>> menus;

    @ApiModelProperty("用户权限")
    private List<String> permissions;

    @ApiModelProperty("角色标识")
    private List<String> roleCodes;

    @ApiModelProperty("登录令牌")
    private Token token;
}
