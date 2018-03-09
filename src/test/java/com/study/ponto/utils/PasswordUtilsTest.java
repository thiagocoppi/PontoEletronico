package com.study.ponto.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilsTest {

    private static final String SENHA = "123456";
    private static final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

    @Test
    public void testGerarSenhaNula() throws Exception{
        Assert.assertNull(PasswordUtils.gerarBCrypt(null));
    }

    @Test
    public void testGerarSenha() throws Exception{
        String hash = PasswordUtils.gerarBCrypt(SENHA);
        Assert.assertTrue(bCrypt.matches(SENHA,hash));
    }
}
