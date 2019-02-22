package com.desperado.teamjob.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PwdUtil {

    public static String encoder(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String enPassword = encoder.encode(password);
        return enPassword;
    }

    public static boolean match(String password,String corrertPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        return encoder.matches(password,corrertPassword);
    }
}
