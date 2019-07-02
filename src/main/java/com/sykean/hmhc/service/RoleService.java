package com.sykean.hmhc.service;


import java.util.List;

import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.req.DictData.DictDataUpateReq;
import com.sykean.hmhc.req.role.RoleReq;
import com.sykean.hmhc.req.role.RoleSaveReq;
import com.sykean.hmhc.req.role.RoleUpdateReq;
import com.sykean.hmhc.res.role.RoleListRes;
import com.sykean.hmhc.res.role.UserIdRoleNameRes;
import com.sykean.hmhc.res.role.UserRoleRes;

public interface RoleService {

    List<RoleListRes> getAllRoles();
    /**
     * 角色查询列表
     * @param req
     * @return
     */
    RestResponse<PageRes<RoleListRes>> queryAll(RoleReq res);
    
    /**
     * 保存角色信息
     * @param req
     * @return
     */
    RestResponse save(RoleSaveReq req);
    /**
     * 更新角色信息
     * @param req
     * @return
     */
    RestResponse update(RoleUpdateReq req);
    
    RestResponse<Integer> deleteRole(List<String>  ids);

    /**
     * 校验角色名称是否重复
     * @param id
     * @param name
     * @param value
     * @return
     */
	boolean checkunique(String id, String name,String value);
	
	RestResponse<List<RoleUpdateReq>> findById(String id);
	
	/**
	 * 通过id获取用户角色信息
	 * @param id
	 * @return
	 */
	RestResponse<List<UserRoleRes>> findUserRoleById(String ids);
}
