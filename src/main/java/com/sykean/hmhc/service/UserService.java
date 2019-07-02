package com.sykean.hmhc.service;

import com.baomidou.mybatisplus.service.IService;
import com.sykean.hmhc.common.PageRes;
import com.sykean.hmhc.common.RestResponse;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.req.user.*;
import com.sykean.hmhc.res.user.UserEditRes;
import com.sykean.hmhc.res.user.UserListRes;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService,IService<User> {

    /**
     * 查询用户列表
     * @param userListReq
     * @return
     */
    RestResponse<PageRes<UserListRes>> selectList(UserListReq userListReq);
    /**
     * @description  根据用户id获取编辑信息
     * @author litong
     * @date 2019-04-11
     * @param id
     * @return com.sykean.zjsjly.common.RestResponse<com.sykean.zjsjly.res.user.UserEditRes>
     */
    RestResponse<UserEditRes> findEditDeatilById(String id);
    /**
     * @description 新增
     * @author litong
     * @date 2019-04-09
     * @param userSaveReq
     */
	RestResponse save(UserSaveReq userSaveReq);

	/**
	 * @description 校验用户名是否重复
	 * @author litong
	 * @date 2019-04-09
	 * @param username
     * @param id
	 */
    boolean validateUsername(String username, String id);
    /**
     * @description  修改
     * @author litong
     * @date 2019-04-09
     * @param userUpdateReq
     * @return com.sykean.zjsjly.common.RestResponse
     */
    RestResponse update(UserUpdateReq userUpdateReq);
    /**
     * @description 删除
     * @author litong
     * @date 2019-04-09
     * @param id
     * @return com.sykean.zjsjly.common.RestResponse
     */
    RestResponse delete(String id);

    User findByUsername(String username);

    boolean validateCerNo(String cerNo, String id);

    RestResponse importData(List<String[]> dataList, String ziptemp);

    RestResponse updateUserState(UpdateUserStateReq updateUserState);

    RestResponse resetPassword(String id);

    RestResponse updatePwd(UpdatePwdReq updatePwdReq);
}
