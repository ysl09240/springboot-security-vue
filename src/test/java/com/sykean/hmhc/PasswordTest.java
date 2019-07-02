package com.sykean.hmhc;

import com.sykean.hmhc.security.MD5PasswordEncoder;
import org.junit.Test;

public class PasswordTest {

    @Test
    public void aa() {
        MD5PasswordEncoder passwordEncoder = new MD5PasswordEncoder();
        System.out.println(passwordEncoder.encode("sy123abc"));
    }
}
