package com.sykean.hmhc.service;

import com.sykean.hmhc.common.MenuTree;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.res.menu.MenuVO;

import java.util.List;

public interface MenuService {
    /**
     * get all menu
     * @return
     */

    List<MenuTree> getAllMenus();
    
    List<MenuVO> findListByRoleId(String roleId);

    RestResponse<Integer> deleteByRoleIds(String roleId);

    List<MenuTree> findTreeByRoleId(String roleId);
}
