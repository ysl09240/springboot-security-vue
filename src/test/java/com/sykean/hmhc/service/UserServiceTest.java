package com.sykean.hmhc.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sykean.hmhc.BaseTest;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.mapper.UserMapper;
import com.sykean.hmhc.req.user.UserListReq;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectList() {
        UserListReq userListReq = new UserListReq();
        userListReq.setPageIndex(1);
        userListReq.setPageSize(10);

        List<User> users = userMapper.selectList(
                new EntityWrapper<>()
        );
    }
}