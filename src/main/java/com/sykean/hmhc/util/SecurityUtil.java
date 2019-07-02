package com.sykean.hmhc.util;

import com.sykean.hmhc.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    /**
     * 获取当前用户
     *
     * @return
     */
    public static User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
