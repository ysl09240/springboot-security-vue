package com.sykean.hmhc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.entity.Role;
import com.sykean.hmhc.req.DictData.DictDataReq;
import com.sykean.hmhc.req.DictData.DictDataSearch;
import com.sykean.hmhc.req.role.RoleReq;
import com.sykean.hmhc.req.role.RoleUpdateReq;
import com.sykean.hmhc.res.role.RoleListRes;
import com.sykean.hmhc.res.role.UserIdRoleNameRes;
import com.sykean.hmhc.res.role.UserRoleRes;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据菜单id获取角色名称
     * @param menuId
     * @return
     */
    @Cacheable(value = "menu", key = "'roleCodes:menuId_'+#menuId")
    List<String> selectByMenuId(@Param("menuId") String menuId);

    /**
     * 根据用户id查询角色code
     * @param userId
     * @return
     */
    @Cacheable(value = "menu", key = "'roleCodes:userId_'+#userId")
    List<String> getAllCodes(@Param("userId") String userId);

    /**
     * 根据用户id查询角色名称
     * @param userIds
     * @return
     */
    List<UserIdRoleNameRes> getAllRolesByUserIds(@Param("userIds") List<String> userIds);
    
    List<RoleListRes> queryAll (RoleReq res);

	void deleteByIds(@Param("ids")List<String> ids);

	List<RoleUpdateReq> findListById(@Param("id")String id);

	List<UserRoleRes> findUserRoleById(@Param("id")String id);
    
    
}
