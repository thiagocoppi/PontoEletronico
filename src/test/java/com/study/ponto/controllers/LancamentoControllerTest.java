package com.study.ponto.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.ponto.api.entities.Empresa;
import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.entities.Lancamento;
import com.study.ponto.api.enums.PerfilEnum;
import com.study.ponto.api.enums.TipoEnum;
import com.study.ponto.dtos.LancamentoDto;
import com.study.ponto.repository.EmpresaRepository;
import com.study.ponto.repository.FuncionarioRepository;
import com.study.ponto.repository.LancamentoRepository;
import com.study.ponto.services.EmpresaService;
import com.study.ponto.services.FuncionarioService;
import com.study.ponto.services.LancamentoService;
import com.study.ponto.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LancamentoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FuncionarioService funcionarioServiceMock;

    @MockBean
    private LancamentoService lancamentoServiceMock;

    private static final long idFuncionario = Long.valueOf(2);
    private static final long idLancamento = Long.valueOf(3);
    private static final TipoEnum TIPO_ENUM = TipoEnum.INICIO_TRABALHO;
    private static final Date DATA = new Date();
    private static final String URL_BASE = "/ponto/api/lancamento";

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/DD HH:MM");
    private static final Logger log = LoggerFactory.getLogger(LancamentoControllerTest.class);

    @Test
    @WithMockUser
    public void adicionarLancamentoTest() throws Exception {
        Lancamento lancamento = getLancamento();
        BDDMockito.given(this.funcionarioServiceMock.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.lancamentoServiceMock.persistir(lancamento)).willReturn(lancamento);
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                                                .content(obterDadosJson())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON))
                                                .andExpect(status().isOk())
                                                .andExpect(jsonPath("$.data.tipo").value(TIPO_ENUM.toString()))
                                                .andExpect(jsonPath("$.data.data").value(simpleDateFormat.format(DATA)))
                                                .andExpect(jsonPath("$.data.funcionarioId").value(idFuncionario))
                                                .andExpect(jsonPath("$.erros").isEmpty());


    }

    @Test
    public void removerLancamentoTest() throws Exception{
        BDDMockito.given(this.lancamentoServiceMock.findById(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE +"/"+ idLancamento)
                                                        .accept(MediaType.APPLICATION_JSON))
                                                        .andExpect(status().isOk());

    }

    @Test
    public void getLancamentoTest() throws Exception{
        Lancamento lancamento = getLancamento();
        BDDMockito.given(this.lancamentoServiceMock.findById(Mockito.anyLong())).willReturn(Optional.of(lancamento));
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + idLancamento)
                                                .accept(MediaType.APPLICATION_JSON))
                                                .andExpect(status().isOk())
                                                .andExpect(jsonPath("$.data.id").value(lancamento.getId()))
                                                .andExpect(jsonPath("$.data.funcionarioId").value(lancamento.getFuncionario().getId()));

    }

    private String obterDadosJson() throws JsonProcessingException {
        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setId(null);
        lancamentoDto.setData(this.simpleDateFormat.format(DATA));
        lancamentoDto.setLocalizacao("Localização de testes");
        lancamentoDto.setDescricao("Descricao de testes");
        lancamentoDto.setTipo(TIPO_ENUM.toString());
        lancamentoDto.setFuncionarioId(idFuncionario);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(lancamentoDto);
    }

    private Lancamento getLancamento(){
        Lancamento lancamento = new Lancamento();
        lancamento.setId(idLancamento);
        lancamento.setLocalizacao("Localizacao Teste");
        lancamento.setDescricao("Descrição de Teste");
        lancamento.setTipo(TIPO_ENUM);
        lancamento.setFuncionario(new Funcionario());
        lancamento.getFuncionario().setId(idFuncionario);
        lancamento.setData(DATA);
        return lancamento;
    }


}
