package com.sykean.hmhc.service.impl;

import com.sykean.hmhc.common.MenuTree;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.mapper.MenuMapper;
import com.sykean.hmhc.res.menu.MenuVO;
import com.sykean.hmhc.service.MenuService;
import com.sykean.hmhc.util.BuildTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

	@Override
	public List<MenuTree> getAllMenus() {
		List<MenuVO> menuList = menuMapper.queryMenuList();
		return BuildTree.bulidTree(menuList);
	}

	@Override
	public List<MenuVO> findListByRoleId(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestResponse<Integer> deleteByRoleIds(String roleId) {
		// TODO Auto-generated method stub
		menuMapper.deleteByRoleIds(roleId);
		return new RestResponse<Integer>();
		
	}

	@Override
	public List<MenuTree> findTreeByRoleId(String roleId) {
		List<MenuVO> menus = menuMapper.findTreeByRoleId(roleId);
		return BuildTree.bulidTree(menus);
	}
}