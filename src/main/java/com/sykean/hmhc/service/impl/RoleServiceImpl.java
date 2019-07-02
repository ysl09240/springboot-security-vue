package com.sykean.hmhc.service.impl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sykean.hmhc.common.Constants;
import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.Role;
import com.sykean.hmhc.entity.RoleMenu;
import com.sykean.hmhc.mapper.MenuMapper;
import com.sykean.hmhc.mapper.RoleMapper;
import com.sykean.hmhc.mapper.RoleMenuMapper;
import com.sykean.hmhc.req.DictData.DictDataUpateReq;
import com.sykean.hmhc.req.role.RoleReq;
import com.sykean.hmhc.req.role.RoleSaveReq;
import com.sykean.hmhc.req.role.RoleUpdateReq;
import com.sykean.hmhc.res.role.RoleListRes;
import com.sykean.hmhc.res.role.UserRoleRes;
import com.sykean.hmhc.service.RoleService;
import com.sykean.hmhc.util.BeanUtils;
import com.sykean.hmhc.util.SecurityUtil;
import com.sykean.hmhc.util.SysUtils;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public List<RoleListRes> getAllRoles() {
        List<Role> roles = roleMapper.selectList(null);
        return BeanUtils.copyListProperties(roles, RoleListRes.class);
    }
	@Override
	public RestResponse<PageRes<RoleListRes>> queryAll(
			RoleReq res) {
			PageHelper.startPage(res.getPageIndex(), res.getPageSize());
			List<RoleListRes> list = roleMapper.queryAll(res);
			
			 long total = new PageInfo<>(list).getTotal(); 
			return RestResponse.success(new PageRes<>(list, total));
       
	}

	@Override
	public RestResponse save(RoleSaveReq req) {
		boolean isExist = checkSamePermissionExist(req.getMenuIds(),"");
		if(isExist){
			return RestResponse.error(ResponseCode.ROLE_PER_EXISTS, "已经存在相同权限的角色");
		}
		Role role = BeanUtils.copyPropertiesByClass(req, Role.class);
		//role.setDesc(req.getDescs());
		role.setId(SysUtils.GetUUID());
		role.setCode(Constants.ROLE_ADMIN_CODE);
		//创建时间
		role.setCreateTime(new Date());
		//创建人
		role.setCreateUserId(SecurityUtil.getUser().getId());
		//保存角色
		roleMapper.insert(role);
		//设置角色
		List<String> list = Arrays.asList(req.getMenuIds().split(","));
//		list=list.stream()
//                .distinct()
//                .collect(Collectors.toList());
		for (String menuId : list) {
			if ("1".equals(menuId)) {
				continue;
			} else {
				roleMenuMapper.insert(new RoleMenu(SysUtils.GetUUID(), role.getId(), menuId));
			}
		}
		return RestResponse.success();
	}

	@Override
	public RestResponse update(RoleUpdateReq req) {
		boolean isExist = checkSamePermissionExist(req.getMenuIds(),req.getId());
		if(isExist){
			return RestResponse.error(ResponseCode.ROLE_PER_EXISTS, "已经存在相同权限的角色");
		}
		Role role = roleMapper.selectById(req.getId());
        if (role==null) {
            return RestResponse.error(ResponseCode.ROLE_NOT_EXISTS, req.getId());
        }
        BeanUtils.copyProperties(req, role);
        //修改人
        
        role.setUpdateUserId(SecurityUtil.getUser().getId());
        //修改时间
        role.setUpdateTime(new Date());
        roleMapper.updateById(role);
        //删除当前用户所对应的的角色
        menuMapper.deleteByRoleIds(role.getId());
       // roleMapper.deleteById(new EntityWrapper<RoleMenu>().eq("role_id", req.getId()));
        //角色
        List<String> list = Arrays.asList(req.getMenuIds().split(","));
        for (String menuId : list) {
        	if("1".equals(menuId)){
        		continue;
        	}else{
        		roleMenuMapper.insert(new RoleMenu(SysUtils.GetUUID(),role.getId(), menuId));
        	}
        }
        return RestResponse.success();
	}

	@Override
	public boolean checkunique(String id, String name, String value) {
		// TODO Auto-generated method stub
		Wrapper<Role> wrapper = new EntityWrapper<Role>().eq(name, value).ne(id != null, "name", id);
        List<Role> rileList = roleMapper.selectList(wrapper);
        return !CollectionUtils.isEmpty(rileList);
	}
	@Override
	public RestResponse<Integer> deleteRole(List<String> ids) {
		// TODO Auto-generated method stub
		if(ids!=null && ids.size()>0){
			roleMapper.deleteByIds(ids);
			for(String id : ids){
				menuMapper.deleteByRoleIds(id);
			}
		}
		return new RestResponse<Integer>();
	}
	@Override
	public RestResponse<List<RoleUpdateReq>> findById(String id) {
		// TODO Auto-generated method stub
		
		List<RoleUpdateReq> res = roleMapper.findListById(id);
		return (RestResponse<List<RoleUpdateReq>>) RestResponse.success (res);
	}
	@Override
	public RestResponse<List<UserRoleRes>> findUserRoleById(String ids) {
		// TODO Auto-generated method stub
		 List<String> list = Arrays.asList(ids.split(","));
		 for(int i =0;i<list.size();i++){
			 List<UserRoleRes> res = roleMapper.findUserRoleById(list.get(i));
				 return (RestResponse<List<UserRoleRes>>) RestResponse.success (res); 
			 
		 }
		return null;
	}
    /**
     * @description  新增、编辑时判断是否已经存在跟当前角色权限相同的角色
     * @author litong
     * @date 2019/6/29
     * @param menuIds
     * @param id
     * @return boolean
     */
	private boolean checkSamePermissionExist(String menuIds, String id) {
		//将角色对应权限menuId转为数组
		String[] menuIdsArray = menuIds.split(",");
		//转为set
		Set<String> menuIdSet = new HashSet(Arrays.asList(menuIdsArray));
		//如果含有顶级角色 1 ，去掉（顶底角色只是在前台显示用，无实际意义）
		Iterator it = menuIdSet.iterator();
		while (it.hasNext()) {
			String str = it.next().toString();
			if ("1".equals(str)) {
				it.remove();
			}
		}
		Wrapper<Role> roleWrapper = new EntityWrapper<>();
		//编辑
		if (StringUtils.isNotBlank(id)) {
			roleWrapper.ne("id", id);
		}
		//查询所有的角色
		List<Role> roleList = roleMapper.selectList(roleWrapper);
		if (!CollectionUtils.isEmpty(roleList)) {
			List<String> roleIdList = roleList.stream().map(Role::getId).collect(Collectors.toList());
			//查询所有角色对应menu权限
			List<RoleMenu> roleMenuList = roleMenuMapper.selectList(new EntityWrapper<RoleMenu>().in("role_id", roleIdList));
			if (!CollectionUtils.isEmpty(roleMenuList)) {
				List<Set<String>> roleMenuSetList = new LinkedList<>();
				//将每一个角色对应的所有menu权限放入Set
				for (String roleId : roleIdList) {
					Set<String> menuSet = new HashSet<>();
					for (RoleMenu roleMenu : roleMenuList) {
						if (roleMenu.getRoleId().equals(roleId)) {
							menuSet.add(roleMenu.getMenuId());
						}
					}
					roleMenuSetList.add(menuSet);
				}
				//将当前角色对应的menu权限和已存在的所有角色 对应权限比对
				if (!CollectionUtils.isEmpty(roleMenuSetList)) {
					for (Set set : roleMenuSetList) {
						if (isSameSetForString(menuIdSet, set)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
    /**
     * @description  判断两个Set<String> 是否包含相同元素
     * @author litong
     * @date 2019/6/29
     * @param set1
     * @param set2
     * @return boolean
     */
	private  boolean isSameSetForString(Set<String> set1, Set<String> set2) {
		if (set1.size() != set2.size()) {
			return false;
		} else {
			for (String str : set1) {
				if (!set2.contains(str)) {
					return false;
				}
			}
			return true;
		}
	}
	
	
}
