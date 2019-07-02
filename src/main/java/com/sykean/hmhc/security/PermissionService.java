package com.sykean.hmhc.security;

import com.sykean.hmhc.entity.Menu;
import com.sykean.hmhc.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private MenuMapper menuMapper;

    public boolean hasPermission(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //当前登录用户所有权限
        List<String> currentPermissions = authentication.getAuthorities().stream().
                filter(item -> !item.getAuthority().contains("ROLE_")).map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        List<Menu> menus = menuMapper.selectList(null);
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getUrl(), request.getRequestURI())) {
                //权限控制
                return currentPermissions.contains(menu.getPermission());
            }
        }
        //不是菜单权限表里面的请求，允许访问
        return true;
    }
}
