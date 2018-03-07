package com.study.ponto.repositories;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.repository.EmpresaRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {
    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CNPJ = "81452217000134";

    @Before
    public void setUp() throws Exception{
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa de Testes");
        empresa.setCnpj(this.CNPJ);
        this.empresaRepository.save(empresa);
    }

    @After
    public final void tearDown(){
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarPorCnpj(){
        Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
        System.out.println(this.CNPJ + "/" + empresa.getCnpj());
        Assert.assertEquals(CNPJ,empresa.getCnpj());
    }
}
