package com.sykean.hmhc.controller;


import com.sykean.hmhc.common.MenuTree;
import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.req.role.RoleReq;
import com.sykean.hmhc.req.role.RoleSaveReq;
import com.sykean.hmhc.req.role.RoleUpdateReq;
import com.sykean.hmhc.res.role.RoleListRes;
import com.sykean.hmhc.res.role.UserRoleRes;
import com.sykean.hmhc.service.MenuService;
import com.sykean.hmhc.service.RoleService;
import com.sykean.hmhc.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("role")
@Validated
@Api(value = "角色接口", description = "角色接口")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "角色列表")

    @PostMapping("list")
    public RestResponse<PageRes<RoleListRes>> list(@RequestBody @Valid RoleReq req) {
        return roleService.queryAll(req);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public RestResponse save(@RequestBody @Valid RoleSaveReq roleSaveReq) {
        return roleService.save(roleSaveReq);
    }

    @ApiOperation(value = "修改角色")
    @PostMapping("update")
    public RestResponse update(@RequestBody @Valid RoleUpdateReq req) {
        return roleService.update(req);
    }

    @ApiOperation(value = "获取菜单树")
    @PostMapping("getMenuTree")
    public RestResponse<List<MenuTree>> getMenuTree() {
        return RestResponse.success(menuService.getAllMenus());
    }

    @ApiOperation(value = "删除")
    @GetMapping("delete")
    public RestResponse<Integer> delete(@RequestParam("ids") String ids) {
        User usermodel = SecurityUtil.getUser();
        RestResponse<Integer> result = roleService.deleteRole(Arrays.asList(ids.split(",")));
        return RestResponse.success();
    }

    @ApiOperation(value = "校验角色名称是否重复")
    @GetMapping("checkunique")
    public RestResponse checkunique(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "name") String name, @RequestParam(value = "value") String value) {
        boolean exists = roleService.checkunique(id, name, value);
        return RestResponse.success(exists);
    }

    @ApiOperation(value = "根据id查询角色信息")
    @GetMapping("findById")
    public RestResponse<List<RoleUpdateReq>> findById(@RequestParam("id") String id) {
        RestResponse<List<RoleUpdateReq>> res = roleService.findById(id);
        return res;
    }

    @ApiOperation(value = "根据id查询用户角色信息")
    @GetMapping("findUserRoleById")
    public RestResponse<List<UserRoleRes>> findUserRoleById(@RequestParam("ids") String ids) {
        RestResponse<List<UserRoleRes>> res = roleService.findUserRoleById(ids);
        return res;
    }

    @ApiOperation("所有角色")
    @GetMapping("all")
    public RestResponse<List<RoleListRes>> findAll() {
        return RestResponse.success(roleService.getAllRoles());
    }
}
