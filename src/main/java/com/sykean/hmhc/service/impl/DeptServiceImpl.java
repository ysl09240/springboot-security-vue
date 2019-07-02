package com.sykean.hmhc.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sykean.hmhc.common.DictConstants;
import com.sykean.hmhc.common.FrontTree;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.common.TableConstants;
import com.sykean.hmhc.entity.Dept;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.mapper.DeptMapper;
import com.sykean.hmhc.mapper.IrisInfoMapper;
import com.sykean.hmhc.mapper.UserMapper;
import com.sykean.hmhc.mapper.WorkerInfoMapper;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.dept.DeptSaveReq;
import com.sykean.hmhc.req.dept.DeptUpdateReq;
import com.sykean.hmhc.res.dept.DeptDetailRes;
import com.sykean.hmhc.res.dept.DeptListRes;
import com.sykean.hmhc.res.dept.DeptTreeRes;
import com.sykean.hmhc.service.DeptService;
import com.sykean.hmhc.util.BeanUtils;
import com.sykean.hmhc.util.BuildTree;
import com.sykean.hmhc.util.DictDataUtil;
import com.sykean.hmhc.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther litong
 * @description 部门逻辑实现类
 * @date 2019-03-07
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public RestResponse<DeptDetailRes> findById(String id) {
        Dept dept = deptMapper.selectById(id);
        if (dept == null) {
            return RestResponse.error(ResponseCode.DEPT_NOT_EXISTS, null);
        }
        DeptDetailRes deptDetailRes = BeanUtils.copyPropertiesByClass(dept, DeptDetailRes.class);
        Dept parentDept = deptMapper.selectById(dept.getSupCode());
        if(parentDept != null){
            //上级部门名称
            deptDetailRes.setSupName(parentDept.getCodeName());
        }
        deptDetailRes.setTypeName(DictDataUtil.queryLabel(DictDataUtil.getDict(DictConstants.SITE_TYPE), deptDetailRes.getType()));
        return RestResponse.success(deptDetailRes);
    }
    
    @Override
    public RestResponse<FrontTree<DeptTreeRes>> tree(String id){
        List<FrontTree<DeptTreeRes>> trees = new ArrayList<>();
        List<DeptTreeRes> deptTreeResList = deptMapper.getChildrenList(SecurityUtil.getUser().getDeptId());
        if(StringUtils.isNotEmpty(id)){
        	List<DeptTreeRes> deptTreeResTempList = deptMapper.getChildrenList(id);
        	deptTreeResTempList.forEach(dept -> deptTreeResList.removeIf(deptL -> deptL.getId().equals(dept.getId())));
        }
        FrontTree<DeptTreeRes> tree = null;
        for(DeptTreeRes deptTreeRes:deptTreeResList){
            tree =  new FrontTree<>();
            tree.setId(deptTreeRes.getId());
            tree.setValue(String.valueOf(deptTreeRes.getId()));
            tree.setParentId(deptTreeRes.getParentId() != null ? String.valueOf(deptTreeRes.getParentId()) : null);
            tree.setLabel(deptTreeRes.getName());
            trees.add(tree);
        }
        return RestResponse.success(BuildTree.buildTree(trees,SecurityUtil.getUser().getDeptId()));

    }
    @Override
    public RestResponse<List<DeptListRes>> list(){
        List<DeptListRes> resultList = new ArrayList<DeptListRes>();
        List<Dept> deptList = deptMapper.selectList(new EntityWrapper<Dept>());
        if(!CollectionUtils.isEmpty(deptList)){
            resultList = BeanUtils.copyListProperties(deptList,DeptListRes.class);
        }
        return RestResponse.success(resultList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse save(DeptSaveReq deptAddReq) {
        Dept dept = new Dept();
        //复制请求对象字段
        BeanUtils.copyProperties(deptAddReq, dept);
        //部门名称去除前后空格
        dept.setCodeName(dept.getCodeName().trim());
        //创建时间
        dept.setCreateTime(new Date());
        //创建人id
        dept.setCreateUserId(SecurityUtil.getUser().getId());
        deptMapper.insert(dept);
//        if(StringUtils.isNotEmpty(dept.getCityId())){
//	        //创建采集信息分表
//	        workerInfoMapper.createTable(TableConstants.BUS_WORKER_INFO  + TableConstants.SPLIT + dept.getCityId());
//	        //创建虹膜信息分表
//	        irisInfoMapper.createTable(TableConstants.BUS_IRIS_INFO + TableConstants.SPLIT + dept.getCityId());
//        }
        return RestResponse.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse update(DeptUpdateReq deptUpdateReq) {
        String id = deptUpdateReq.getCode();
        Dept dept = deptMapper.selectById(id);
        BeanUtils.copyProperties(deptUpdateReq, dept);
        //部门名称去除前后空格
        dept.setCodeName(dept.getCodeName().trim());
        dept.setUpdateTime(new Date());
        dept.setUpdateUserId(SecurityUtil.getUser().getId());
        deptMapper.update(dept);
        return RestResponse.success();
    }

    @Override
    public boolean validate(String id, String name,String value) {
        Wrapper<Dept> wrapper = new EntityWrapper<Dept>().eq(name, value).ne(id != null, "code", id);
        List<Dept> deptList = deptMapper.selectList(wrapper);
        return !CollectionUtils.isEmpty(deptList);
    }

    @Override
    public boolean validateParent(String parentId, String id) {
        boolean result = false;
        //所选上级部门是当前部门
        if (!parentId.equals(id)) {
            //查询所有子集
            List<DeptTreeRes> deptTreeResList = deptMapper.getChildrenList(id);
            //当前部门存在子集
            if (!CollectionUtils.isEmpty(deptTreeResList)) {
                Set<String> childrenIdList = deptTreeResList.stream().map(DeptTreeRes::getId).collect(Collectors.toSet());
                //当前部门子集不包含所选父级部门
                if (!childrenIdList.contains(parentId)) {
                    result = true;
                }
                //当前部门id不存在子集
            } else{
                result = true;
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse delete(String id) {
        Dept dept = deptMapper.selectById(id);
        List<Dept> childList = deptMapper.selectList(new EntityWrapper<Dept>().eq("sup_code", id));
        //部门存在子级
        if (!CollectionUtils.isEmpty(childList)) {
            return RestResponse.error(ResponseCode.DEPT_SUBSET_EXIST, null);
        }
        List<User> userList = userMapper.selectList(new EntityWrapper<User>().eq("dept_id",id));
        //存在关联的用户
        if(!CollectionUtils.isEmpty(userList)){
           return RestResponse.error(ResponseCode.DEPT_RELATION_USERS_EXIST,null);
        }
        this.deleteById(id);
        return RestResponse.success();
    }

    @Override
    public String getDeptNamesByIds(List<Integer> deptIds) {
        String result = "";
        StringBuilder deptNames = new StringBuilder();
        List<Dept> deptList = deptMapper.selectList(new EntityWrapper<Dept>().in("id", deptIds));
        if (!CollectionUtils.isEmpty(deptList)) {
            List<String> deptNameList = deptList.stream().map(Dept::getCodeName).collect(Collectors.toList());
            for (String deptName : deptNameList) {
                deptNames.append(deptName);
                deptNames.append("、");
            }
            result = String.valueOf(deptNames);
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }
    @Override
    public Map<String,String> getAllDeptNamesAndIds(){
        List<Dept> deptList = deptMapper.selectList(new EntityWrapper<>());
        Map<String, String> deptMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(deptList)) {
            for (Dept dept : deptList) {
                deptMap.put(dept.getCodeName(), dept.getCode());
            }
        }
        return deptMap;
    }
    /**
     * @description  获取顶级部门
     * @author litong
     * @date 2019-03-21
     * @param
     * @return com.sykean.hmhc.entity.Dept
     */
    private Dept getTopDept(){
        Dept dept = new Dept();
        List<Dept> deptList = deptMapper.selectList(new EntityWrapper<Dept>().isNull("sup_code"));
        if(!CollectionUtils.isEmpty(deptList)){
            dept = deptList.get(0);
        }
        return dept;
    }

	@Override
	public List<DictDataReq> sortList(String id) {
		List<DictDataReq> result = deptMapper.sortList(id);
		DictDataReq last = CollectionUtils.lastElement(result);
		if(last == null){
			last = new DictDataReq();
			last.setId("1");
			last.setName("1");
			result.add(last);
		}else{
			DictDataReq newLast = new DictDataReq();
			newLast.setId(Integer.parseInt(last.getId()) + 1 + "");
			newLast.setName(Integer.parseInt(last.getId()) + 1 + "");
			result.add(newLast);
		}
		return result;
	}

    @Override
    public RestResponse<FrontTree<DeptTreeRes>> currentUserdeptTree() {
        List<FrontTree<DeptTreeRes>> trees = new ArrayList<>();
        List<DeptTreeRes> deptTreeResList = deptMapper.getChildrenList(SecurityUtil.getUser().getDeptId());
        FrontTree<DeptTreeRes> tree = null;
        for (DeptTreeRes deptTreeRes : deptTreeResList) {
            tree = new FrontTree<>();
            tree.setId(deptTreeRes.getId());
            tree.setValue(String.valueOf(deptTreeRes.getId()));
            tree.setParentId(deptTreeRes.getParentId() != null ? String.valueOf(deptTreeRes.getParentId()) : null);
            tree.setLabel(deptTreeRes.getName());
            trees.add(tree);
        }
        return RestResponse.success(BuildTree.buildTree(trees, SecurityUtil.getUser().getDeptId()));
    }
}
