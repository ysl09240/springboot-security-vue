package com.sykean.hmhc.mapper;

import com.sykean.hmhc.BaseTest;
import com.sykean.hmhc.res.menu.MenuVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MenuMapperTest extends BaseTest {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void findTreeByRoleId() {
        final List<MenuVO> menus = menuMapper.findTreeByRoleId("3A7A225991A311E9AC5E2C4D549D95E3");
    }
}