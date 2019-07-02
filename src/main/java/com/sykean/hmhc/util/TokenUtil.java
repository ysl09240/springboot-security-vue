package com.sykean.hmhc.util;

import com.sykean.hmhc.security.Token;

import java.util.UUID;

public class TokenUtil {

    /**
     * 获取token
     *
     * @return
     */
    public static Token generateToken() {
        return new Token(UUID.randomUUID().toString());
    }
}
