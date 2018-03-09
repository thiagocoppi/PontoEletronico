package com.study.ponto.service;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.repository.EmpresaRepository;
import com.study.ponto.services.EmpresaService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    private static final String CNPJ = "00191974000108";

    @Before
    public void setUp()throws Exception{
        BDDMockito.given(this.empresaRepository.findByCnpj(Mockito.anyString())).willReturn(new Empresa());
        BDDMockito.given(this.empresaRepository.save(Mockito.any(Empresa.class))).willReturn(new Empresa());
    }

    @Test
    public void testProcurarEmpresaCNPJ () throws Exception{
        Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(this.CNPJ);
        Assert.assertTrue(empresa.isPresent());
    }

    @Test
    public void testPersistirEmpresa() throws Exception{
        Empresa empresa = empresaService.persistirEmpresa(new Empresa());
        Assert.assertNotNull(empresa);
    }


}
