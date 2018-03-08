package com.study.ponto.repositories;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.entities.Lancamento;
import com.study.ponto.api.enums.PerfilEnum;
import com.study.ponto.api.enums.TipoEnum;
import com.study.ponto.repository.EmpresaRepository;
import com.study.ponto.repository.FuncionarioRepository;
import com.study.ponto.repository.LancamentoRepository;
import com.study.ponto.utils.PasswordUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Long funcionarioId;

    @Before
    public void setUp() throws Exception{
        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        Funcionario func = this.funcionarioRepository.save(obterDadosFuncionario(empresa));

        this.funcionarioId = func.getId();

        this.lancamentoRepository.save(obterDadosLancamentos(func));
        this.lancamentoRepository.save(obterDadosLancamentos(func));
    }

    @After
    public void tearDown(){
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarLancamentoPorFuncionarioId(){
        List<Lancamento> lancamentoList = this.lancamentoRepository.findFuncionarioById(this.funcionarioId);

        Assert.assertEquals(2, lancamentoList.size());
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioIdPaginado(){
        PageRequest page = PageRequest.of(0,10);
        Page<Lancamento> lancamentoPage = this.lancamentoRepository.findFuncionarioById(this.funcionarioId,page);

        Assert.assertEquals(2,lancamentoPage.getTotalElements());
    }

    private Funcionario obterDadosFuncionario(Empresa empresa) throws NoSuchAlgorithmException {
        Funcionario func = new Funcionario();
        func.setNome("Teste Unitario");
        func.setPerfil(PerfilEnum.ROLE_USUARIO);
        func.setSenha(PasswordUtils.gerarBCrypt("123456"));
        func.setQtdHorasAlmoco((float) 2.5);
        func.setQtdHorasTrabalhoDia((float)8.0);
        func.setValorHora(new BigDecimal("0.5"));
        func.setCpf("07052429942");
        func.setEmail("email@senior.com.br");
        func.setEmpresa(empresa);
        return func;
    }

    private Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa do Teste Unitario");
        empresa.setCnpj("58382075000164");
        return empresa;
    }

    private Lancamento obterDadosLancamentos(Funcionario funcionario){
        Lancamento lancamento = new Lancamento();
        lancamento.setData(new Date());
        lancamento.setTipo(TipoEnum.TERMINO_ALMOCO);
        lancamento.setFuncionario(funcionario);
        lancamento.setDescricao("Lançamento de Teste");
        lancamento.setLocalizacao("Localização de Teste");
        return lancamento;
    }
}
