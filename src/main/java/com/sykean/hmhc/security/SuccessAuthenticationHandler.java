package com.sykean.hmhc.security;

import com.sykean.hmhc.common.Constants;
import com.sykean.hmhc.common.MenuTree;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.entity.Dept;
import com.sykean.hmhc.entity.Menu;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.mapper.DeptMapper;
import com.sykean.hmhc.mapper.MenuMapper;
import com.sykean.hmhc.mapper.RoleMapper;
import com.sykean.hmhc.res.login.UserInfo;
import com.sykean.hmhc.res.menu.MenuVO;
import com.sykean.hmhc.service.UserService;
import com.sykean.hmhc.util.BuildTree;
import com.sykean.hmhc.util.ResponseUtil;
import com.sykean.hmhc.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class SuccessAuthenticationHandler implements AuthenticationSuccessHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserInfo userInfo = new UserInfo();
        String username = "";
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else if (principal instanceof Principal) {
            username = ((Principal) principal).getName();
        }
        userInfo.setUsername(username);
        User user = userService.findByUsername(username);
        //封装到用户信息里
        userInfo.setId(user.getId());
        //用户姓名
        userInfo.setRealName(user.getRealName());
        //警员编号
        userInfo.setJzCode(user.getJzCode());
        //部门
        userInfo.setDeptId(user.getDeptId());
        Dept dept = deptMapper.selectById(user.getDeptId());
        if (dept != null) {
            userInfo.setDeptName(dept.getCodeName());
        }
        //查询角色code
        List<String> roleCodes = roleMapper.getAllCodes(user.getId());
        userInfo.setRoleCodes(roleCodes);
        //查询封装菜单信息
        List<Menu> menus = menuMapper.loadMenus(user.getId());
        List<MenuTree<MenuVO>> trees = new ArrayList<>();
        for (Menu menu : menus) {
            MenuTree<MenuVO> tree = new MenuTree<>();
            tree.setId(menu.getId());
            tree.setParentId(menu.getParentId());
            tree.setLabel(menu.getLabel());
            tree.setCode(menu.getCode());
            tree.setIcon(menu.getIcon());
            tree.setPath(menu.getPath());
            trees.add(tree);
        }
        List<MenuTree<MenuVO>> menuTrees = BuildTree.buildMenuList(trees, "-1");
        BuildTree.removeEmptyChildren(menuTrees);
        userInfo.setMenus(menuTrees);
        //权限
        List<String> permissions = menuMapper.findPermissions(user.getId());
        userInfo.setPermissions(permissions);
        //是否已经登录
        String existsToken = stringRedisTemplate.opsForValue().get(Constants.USERNAME_TOKEN_PREFIX + username);
        Long expireTime = stringRedisTemplate.getExpire(Constants.USERNAME_TOKEN_PREFIX + username, TimeUnit.MILLISECONDS);
        if (StringUtils.isNotBlank(existsToken)) {
            userInfo.setToken(new Token(existsToken, expireTime));
            ResponseUtil.printRes(response, ResponseCode.SUCCESS, true, userInfo);
            return;
        }
        Token token = TokenUtil.generateToken();
        ;
        userInfo.setToken(token);
        //保存token：username
        stringRedisTemplate.opsForValue().set(Constants.TOKEN_USERNAME_PREFIX + token.getAccessToken(), username, 1, TimeUnit.HOURS);
        //再保存username: token
        stringRedisTemplate.opsForValue().set(Constants.USERNAME_TOKEN_PREFIX + username, token.getAccessToken(), 1, TimeUnit.HOURS);
        //认证对象放入redis
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        operations.set(Constants.Security.AUTH_TOKEN_KEY + token.getAccessToken(), user, 1, TimeUnit.HOURS);
        ResponseUtil.printRes(response, ResponseCode.SUCCESS, true, userInfo);
    }
}
