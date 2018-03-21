package com.study.ponto.controllers;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.repository.EmpresaRepository;
import com.study.ponto.services.EmpresaService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaControllerTest {

    @MockBean
    private EmpresaService empresaService;

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CNPJ = "59680144000189";
    private static final String RAZAO_SOCIAL = "Empresa de Test S.A";
    private static final Long idEmpresa = 1L;


    @Before
    public void setUp(){

    }

    @Test
    public void testBuscarPorCNPJInvalido(){
        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(CNPJ);
        Assert.assertEquals(empresa.isPresent(), false);
    }

    @Test
    public void testBuscarCNPJValido(){

    }

    private Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setId(idEmpresa);
        empresa.setRazaoSocial(RAZAO_SOCIAL);
        empresa.setCnpj(CNPJ);
        return empresa;
    }


}
