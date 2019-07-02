package com.sykean.hmhc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.res.user.UserListRes;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名获取角色信息
     * @param username
     * @return
     */
    @Cacheable(value = "user", key = "'roles:username__'+#username")
    List<String> selectRoles(@Param("username") String username);
    
    int update(User user);
    /**
     * @description  查询列表页数据
     * @author litong
     * @date 2019-04-11
     * @param id
     * @return java.util.List<com.sykean.zjsjly.res.user.UserListRes>
     */
    List<UserListRes> getUserList(@Param("id") String id);
}
