package com.sykean.hmhc.service;

import com.baomidou.mybatisplus.service.IService;
import com.sykean.hmhc.common.FrontTree;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.Dept;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.dept.DeptSaveReq;
import com.sykean.hmhc.req.dept.DeptUpdateReq;
import com.sykean.hmhc.res.dept.DeptDetailRes;
import com.sykean.hmhc.res.dept.DeptListRes;
import com.sykean.hmhc.res.dept.DeptTreeRes;

import java.util.List;
import java.util.Map;

public interface DeptService extends IService<Dept> {

    /**
     * @description  获取部门详情
     * @author litong
     * @date 2019-03-11
     * @param id
     * @return com.sykean.hmhc.common.RestResponse<com.sykean.hmhc.res.dept.DeptDetailRes>
     */
    RestResponse<DeptDetailRes> findById(String id);

    /**
     * @description  获取部门树
     * @author litong
     * @date 2019-03-11
     * @param
     * @return com.sykean.hmhc.common.RestResponse<com.sykean.hmhc.res.dept.DeptTreeRes>
     */
    RestResponse<FrontTree<DeptTreeRes>> tree(String id);
    /**
     * @description  获取部门列表
     * @author litong
     * @date 2019-03-11
     * @param
     * @return com.sykean.hmhc.common.RestResponse<java.util.List<com.sykean.hmhc.res.dept.DeptListRes>>
     */
    RestResponse<List<DeptListRes>> list();
    /**
     * @param deptAddReq 部门新增请求体
     * @return com.sykean.hmhc.common.RestResponse
     * @description 新增部门
     * @author litong
     * @date 2019-03-07
     */
    RestResponse save(DeptSaveReq deptAddReq);

    /**
     * @param deptUpdateReq 部门修改请求体
     * @return com.sykean.hmhc.common.RestResponse
     * @description 修改部门
     * @author litong
     * @date 2019-03-07
     */
    RestResponse update(DeptUpdateReq deptUpdateReq);

    /**
     * @param id   id
     * @param name 部门名称
     * @return boolean
     * @description 校验部门数据是否重复
     * @author litong
     * @date 2019-03-11
     */
    boolean validate(String id, String name,String value);
    /**
     * @description  修改判断上级部门是否合理(上级部门不能选择自己本身和下级部门)
     * @author litong
     * @date 2019-03-21
     * @param parentId
     * @param id
     * @return boolean
     */
    boolean validateParent(String parentId,String id);

    /**
     * @param id
     * @return com.sykean.hmhc.common.RestResponse
     * @description 删除部门
     * @author litong
     * @date 2019-03-11
     */
    RestResponse delete(String id);
    /**
     * @description  根据部门id数组获取部门名称
     * @author litong
     * @date 2019-03-13
     * @param deptIds
     * @return java.lang.String
     */
    String getDeptNamesByIds(List<Integer> deptIds);

    /**
     * @description   获取所有部门数据
     * @author litong
     * @date 2019-03-15
     * @param
     * @return java.util.Map<java.lang.String,java.lang.Integer>
     */
    Map<String,String>  getAllDeptNamesAndIds();

	List<DictDataReq> sortList(String id);

    /**
     * 查找当前用户的组织树
     *
     * @return
     */
    RestResponse<FrontTree<DeptTreeRes>> currentUserdeptTree();
}
