package com.sykean.hmhc.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sykean.hmhc.entity.UserRole;
import com.sykean.hmhc.res.role.UserRoleRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    List<UserRoleRes> findByUserIds(@Param("userIds") List<String> userIds);


}
