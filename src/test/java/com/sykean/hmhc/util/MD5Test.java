package com.sykean.hmhc.util;

import org.junit.Test;
import org.springframework.util.DigestUtils;

public class MD5Test {

    @Test
    public void encode(){
        String password = "123456";
        System.out.println(DigestUtils.md5DigestAsHex(password.getBytes()));
    }
}
