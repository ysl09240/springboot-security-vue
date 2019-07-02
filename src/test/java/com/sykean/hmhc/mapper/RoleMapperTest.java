package com.sykean.hmhc.mapper;

import com.sykean.hmhc.BaseTest;
import com.sykean.hmhc.res.role.UserIdRoleNameRes;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RoleMapperTest extends BaseTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void getAllRolesByUserIds() {
        List<String> userIds = new ArrayList<String>() {
            private static final long serialVersionUID = -8188727797514719139L;

            {
                add("1");
                add("2");
                add("3");
        }};
        List<UserIdRoleNameRes> userRole = roleMapper.getAllRolesByUserIds(userIds);
    }
}