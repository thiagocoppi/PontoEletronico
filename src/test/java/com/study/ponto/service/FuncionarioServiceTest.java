package com.study.ponto.service;

import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.repository.FuncionarioRepository;
import com.study.ponto.services.FuncionarioService;
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
public class FuncionarioServiceTest {
    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    private final static String EMAIL = "email@email.com";
    private final static String CPF = "25440000070";
    private final static long ID = 31232432;

    @Before
    public void setUp(){
        BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.findByCpfOrEmail(Mockito.anyString(),Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.findById(Mockito.anyLong())).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
    }

    @Test
    public void testBuscarPorCpf() throws Exception{
        Optional<Funcionario> funcionario = funcionarioService.findByCpf(CPF);
        Assert.assertNotNull(funcionario.isPresent());
    }

    @Test
    public void testBuscarPorEmail() throws Exception{
        Optional<Funcionario> funcionario = funcionarioService.findByEmail(EMAIL);
        Assert.assertNotNull(funcionario.isPresent());
    }

    @Test
    public void testBuscarPorEmailOuCpf() throws Exception{
        Optional<Funcionario> funcionario = funcionarioService.findByEmailOrCpf(EMAIL, CPF);
        Assert.assertNotNull(funcionario.isPresent());
    }
    @Test
    public void testBuscarPorId() throws Exception {
        Optional<Funcionario> funcionario = funcionarioService.findById(ID);
        Assert.assertNotNull(funcionario.isPresent());
    }

    @Test
    public void testPersistirFuncionario() throws Exception{
        Optional<Funcionario> funcionario = funcionarioService.persistir(new Funcionario());
        Assert.assertNotNull(funcionario.isPresent());
    }

}
