package com.sykean.hmhc.controller;

import com.sykean.hmhc.common.FrontTree;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.dept.DeptSaveReq;
import com.sykean.hmhc.req.dept.DeptUpdateReq;
import com.sykean.hmhc.res.dept.DeptDetailRes;
import com.sykean.hmhc.res.dept.DeptListRes;
import com.sykean.hmhc.res.dept.DeptTreeRes;
import com.sykean.hmhc.service.DeptService;
import com.sykean.hmhc.util.SecurityUtil;
import com.sykean.hmhc.util.SysUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther litong
 * @description 部门控制类
 * @date 2019-03-07
 */
@RestController
@RequestMapping("dept")
@Api(value = "部门接口", description = "部门接口")
@Validated
public class DeptController {
    @Autowired
    private DeptService deptService;

    @ApiOperation(value= "部门详情")
    @GetMapping("detail")
    public RestResponse<DeptDetailRes> detail(String id){
    	 if(StringUtils.isEmpty(id)){
    		 id = SecurityUtil.getUser().getDeptId();
    	 }
         return deptService.findById(id);
    }

    @ApiOperation(value= "部门树")
    @GetMapping("tree")
    public RestResponse<FrontTree<DeptTreeRes>> tree(String id){
        return deptService.tree(id);
    }

    @ApiOperation(value = "查找当前用户的部门树")
    @GetMapping("user/tree")
    public RestResponse<FrontTree<DeptTreeRes>> currentUserdeptTree() {
        return deptService.currentUserdeptTree();
    }

    @ApiOperation(value= "部门列表")
    @GetMapping("list")
    public RestResponse<List<DeptListRes>> list(){
       return deptService.list();
    }

    @ApiOperation(value = "新增部门")
    @PostMapping("save")
    public RestResponse save(@RequestBody DeptSaveReq deptAddReq) {
    	deptAddReq.setCode(SysUtils.GetUUID());
        return deptService.save(deptAddReq);
    }

    @ApiOperation(value = "修改部门")
    @PostMapping("update")
    public RestResponse update(@RequestBody DeptUpdateReq deptUpdateReq) {
        return deptService.update(deptUpdateReq);
    }

    @ApiOperation(value ="修改校验上级部门")
    @PostMapping("validate_parent")
    public RestResponse validateParent(@RequestParam(value = "parentId") String parentId,
                                       @RequestParam(value = "id") String id){
        boolean result = deptService.validateParent(parentId, id);
        return RestResponse.success(result);
    }

    @ApiOperation(value = "校验机构名称/机构代码是否重复")
    @GetMapping("validate")
    public RestResponse validate(@RequestParam(value = "id", required = false) String id,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "value") String value) {
        boolean exists = deptService.validate(id, name,value);
        return RestResponse.success(exists);
    }

    @ApiOperation(value = "删除部门")
    @PostMapping("delete")
    public RestResponse delete(@RequestBody DeptSaveReq deptSaveReq) {
        return deptService.delete(deptSaveReq.getCode());
    }
    
    @ApiOperation(value = "删除部门")
    @GetMapping("sortList")
    public RestResponse<List<DictDataReq>> sortList(String id) {
        return RestResponse.success(deptService.sortList(id));
    }
    

}
