package com.sykean.hmhc.mapper;

import com.sykean.hmhc.BaseTest;
import com.sykean.hmhc.res.role.UserRoleRes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserRoleMapperTest extends BaseTest {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    public void findByUserIds() {
        List<UserRoleRes> userRoleResList = userRoleMapper.findByUserIds(new ArrayList<String>() {{
            add("8895b1097aa0dc7012a7b86cc06eb457");
        }});
        userRoleResList.forEach(System.out::println);
    }
}