package com.sykean.hmhc.security;

import com.sykean.hmhc.common.Constants;
import com.sykean.hmhc.common.ResponseCode;
import com.sykean.hmhc.entity.User;
import com.sykean.hmhc.mapper.MenuMapper;
import com.sykean.hmhc.mapper.RoleMapper;
import com.sykean.hmhc.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TokenFileter implements Filter, InitializingBean {

    private static final String FILTER_APPLIED = "__tokenfilter_filterApplied";
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("UserTokenValidateInterceptor init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getAttribute(FILTER_APPLIED) != null) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            chain.doFilter(request, response);
        } else {
            request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
            if (!isIgnoredUrl(request.getRequestURI())) {
                String token = request.getHeader(Constants.TOKEN_KEY);
                if (StringUtils.isBlank(token)) {
                    chain.doFilter(request, response);
                    return;
                }
                Authentication authentication = null;
                try {
                    ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
                    User user = (User) operations.get(Constants.Security.AUTH_TOKEN_KEY + token);
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    List<String> permissions = menuMapper.findPermissions(user.getId());
                    permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));
                    List<String> roleCodes = roleMapper.getAllCodes(user.getId());
                    roleCodes.forEach(roleCode -> authorities.add(new SimpleGrantedAuthority("ROLE_" + roleCode)));
                    //create authentication
                    authentication = new UsernamePasswordAuthenticationToken(
                            user, user.getUsername(),
                            authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    //没有权限的不做处理
                }
            }

            chain.doFilter(request, response);
        }
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        ResponseUtil.printRes(response, ResponseCode.TOEKN_INVALID, false, null);
    }

    @Override
    public void destroy() {

    }

    /**
     * 判断当前请求是否进入该拦截器
     *
     * @param requestURI
     * @return
     */
    private boolean isIgnoredUrl(String requestURI) {
        for (String auth : Constants.Security.WITHOUT_AUTH) {
            if (antPathMatcher.match(auth, requestURI)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
