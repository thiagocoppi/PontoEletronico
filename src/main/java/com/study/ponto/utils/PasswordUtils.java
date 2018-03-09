package com.study.ponto.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    public static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    public PasswordUtils(){}

    public static String gerarBCrypt(String senha){
        if(senha == null)
            return null;
        log.info("Gerando hash");
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        return bCrypt.encode(senha);
    }
}
