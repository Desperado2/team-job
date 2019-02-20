package com.desperado.teamjob;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPwdEncode {

    @Test
    public void test(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String enPassword = encoder.encode("admin");
        System.out.println(enPassword);
    }
}
