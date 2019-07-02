package com.sykean.hmhc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.common.Constants;
import com.sykean.hmhc.entity.Menu;
import com.sykean.hmhc.res.menu.MenuVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户名查询对应的菜单
     * @param userId
     * @return
     */
    @Cacheable(value = Constants.Cache.CACHE_NAME_MENU, key = "'menus:userId_'+ #userId")
    List<Menu> loadMenus(@Param("userId") String userId);

    @Cacheable(value = Constants.Cache.CACHE_NAME_MENU, key = "'permissions:userId_'+ #id")
    List<String> findPermissions(@Param("userId") String id);


	List<MenuVO> queryMenuList();

    void deleteByRoleIds(@Param("roleId") String roleId);

    List<MenuVO> findTreeByRoleId(@Param("roleId") String roleId);
}
