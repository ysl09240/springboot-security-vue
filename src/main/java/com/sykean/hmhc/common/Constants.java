package com.sykean.hmhc.common;

import java.util.HashSet;
import java.util.Set;

public interface Constants {
    //userId：token的key前缀
    String USERNAME_TOKEN_PREFIX = "hmhc_username_token:";

    //token: username key的前缀
    String TOKEN_USERNAME_PREFIX = "hmhc_token_username:";

    String OLD_REFRESH_TOKEN_PREFIX = "";

    int OLD_REFRESH_TOKEN_EXPIRE = 15;

    String TOKEN_KEY = "token";

    interface Security{

        Long DEFAULT_TOKEN_EXPIRETIME = 3600000L;

        String AUTH_TOKEN_KEY = "hmhc:auth_";

        String[] WITHOUT_AUTH = {
                "/swagger-ui.html",
                "/favicon.ico",
                "/mappings",
                "/swagger/**",
                "/v2/**",
                "/webjars/**",
                "/swagger-resources/**",
                "/error"
        };
    }

    //用户默认密码
    String USER_DEFAULT_PASSWORD = "sy123abc";
    //超级管理员角色code
    String ROLE_SUPER_ADMIN_CODE = "ROLE_SUPER_ADMIN";
    //普通管理员角色code
    String ROLE_ADMIN_CODE = "ROLE_ADMIN";

    interface Cache{

        String CACHE_NAME_MENU = "menu";

        String CACHE_KEY_ALL_MENU = "allMneus";
    }

    interface File {
        //用户批量导入允许图片的格式
        Set<String> IMPORT_IMAGE_TYPE = new HashSet<String>() {{
            add("jpg");
            add("JPG");
            add("jpeg");
            add("png");
            add("bmp");
            add("tiff");
            add("tga");
            add("eps");
            add("psd");
        }};
    }

}
