package com.study.ponto.repositories;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.enums.PerfilEnum;
import com.study.ponto.repository.EmpresaRepository;
import com.study.ponto.repository.FuncionarioRepository;
import com.study.ponto.utils.PasswordUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String EMAIL = "email@teste.com";
    private static final String CPF = "36288413569";

    @Before
    public void setUp() throws Exception{
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        this.funcionarioRepository.save(obterDadosFuncionario(empresa));
    }

    @After
    public final void tearDown(){
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarPorEmail(){
        Funcionario func = this.funcionarioRepository.findByEmail(this.EMAIL);
        Assert.assertEquals(func.getEmail(),this.EMAIL);
    }

    @Test
    public void testBuscarPorCpf(){
        Funcionario func = this.funcionarioRepository.findByCpf(this.CPF);
        Assert.assertEquals(func.getCpf(),this.CPF);
    }

    @Test
    public void testBuscarPorCpfEEmail(){
        Funcionario func = this.funcionarioRepository.findByCpfOrEmail(this.CPF,this.EMAIL);
        Assert.assertNotNull(func);
    }

    @Test
    public void testBuscarPorCpfEEmailInvalido(){
        Funcionario func = this.funcionarioRepository.findByCpfOrEmail(this.CPF,"testeerrado@gmail.com");
        Assert.assertNotNull(func);
    }

    @Test
    public void testBuscarParaCpfInvalidoEEmailValido(){
        Funcionario func = this.funcionarioRepository.findByCpfOrEmail("1234569351",this.EMAIL);
        Assert.assertNotNull(func);
    }

    @Test
    public void testBuscarCpfEEmailInvalido(){
        Funcionario func = this.funcionarioRepository.findByCpfOrEmail("123156","teste@invalido.com");
        Assert.assertNull(func);
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario func = new Funcionario();
        func.setNome("Teste Unitario");
        func.setPerfil(PerfilEnum.ROLE_USUARIO);
        func.setSenha(PasswordUtils.gerarBCrypt("123456"));
        func.setQtdHorasAlmoco((float) 2.5);
        func.setQtdHorasTrabalhoDia((float)8.0);
        func.setValorHora(new BigDecimal("0.5"));
        func.setCpf(this.CPF);
        func.setEmail(this.EMAIL);
        func.setEmpresa(empresa);
        return func;
    }

    private Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa do Teste Unitario");
        empresa.setCnpj("58382075000164");
        return empresa;
    }
}