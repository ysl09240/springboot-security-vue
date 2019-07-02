package com.sykean.hmhc.controller;

import com.sykean.hmhc.common.MenuTree;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("当前登录角色的菜单权限")
    @GetMapping("role/id")
    public RestResponse<List<MenuTree>> findTreeByRoleId(@RequestParam String roleId) {
        return RestResponse.success(menuService.findTreeByRoleId(roleId));
    }
}
