package com.sykean.hmhc.security;

import com.sykean.hmhc.common.Constants;
import lombok.Data;

@Data
public class Token {
    private String accessToken;

    private Long expireTime;

    public Token() {

    }

    public Token(String accessToken) {
        this.accessToken = accessToken;
        this.expireTime = Constants.Security.DEFAULT_TOKEN_EXPIRETIME;
    }

    public Token(String accessToken, Long expireTime) {
        this.accessToken = accessToken;
        this.expireTime = expireTime;
    }
}