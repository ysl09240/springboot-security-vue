package com.sykean.hmhc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.entity.Dept;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.res.dept.DeptListRes;
import com.sykean.hmhc.res.dept.DeptTreeRes;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {
    /**
     * @description  更新部门
     * @author litong
     * @date 2019-03-20
     * @param dept
     * @return int
     */
    int update(Dept dept);
     /**
      * @description  根据父节点查询所有子节点树
      * @author litong
      * @date 2019-03-11
      * @param id
      * @return java.util.List<com.sykean.hmhc.res.dept.DeptTreeRes>
      */
    List<DeptTreeRes> getChildrenList(String id);
    /**
     * @description  根据子节点查询所有父节点id
     * @author litong
     * @date 2019-03-15
     * @param id
     * @return java.util.List<java.lang.Integer>
     */
    List<String> getParentIds(String id);

    List<DeptListRes> findChildren(@Param("deptId") String deptId);
    
	List<DictDataReq> sortList(String id);
}
