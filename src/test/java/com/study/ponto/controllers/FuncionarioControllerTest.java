package com.study.ponto.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.ponto.api.entities.Empresa;
import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.enums.PerfilEnum;
import com.study.ponto.services.FuncionarioService;
import com.study.ponto.utils.PasswordUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles("test")
public class FuncionarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FuncionarioService funcionarioService;

    private static final String URL_BASE = "/ponto/api/funcionario";

    @Test
    public void testBuscarFuncionarioCPF() throws Exception{
        Funcionario funcionario = obterFuncionario();
        BDDMockito.given(this.funcionarioService.findByCpf(Mockito.anyString())).willReturn(Optional.of(funcionario));
        BDDMockito.given(this.funcionarioService.converterFuncionarioDTO(Mockito.any(Funcionario.class))).willReturn(funcionario);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/59141628071")
                                                .contentType(MediaType.APPLICATION_JSON))
                                                .andExpect(status().isOk())
                                                .andExpect(jsonPath("$.data.cpf").value("59141628071"))
                                                .andExpect(jsonPath("$.data.email").value("email@gmail.com"));
    }

    @Test
    public void testAlterarFuncionarioID() throws Exception{
        Funcionario funcionario = obterFuncionario();
        BDDMockito.given(this.funcionarioService.findById(Mockito.anyLong())).willReturn(Optional.of(funcionario));
        BDDMockito.given(this.funcionarioService.converterFuncionarioDTO(Mockito.any(Funcionario.class))).willReturn(funcionario);
        mockMvc.perform(MockMvcRequestBuilders.put(URL_BASE +"/"+ String.valueOf(funcionario.getId()))
                                                .accept(MediaType.APPLICATION_JSON)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(convertToJSON(funcionario)))
                                                .andExpect(status().isOk())
                                                .andExpect(jsonPath("$.data.id").value(String.valueOf(funcionario.getId())))
                                                .andExpect(jsonPath("$.data.cpf").value(funcionario.getCpf()))
                                                .andExpect(jsonPath("$.data.email").value(funcionario.getEmail()))
                                                .andExpect(jsonPath("$.data.nome").value("Teste de Alteracao"));


    }

    private Funcionario obterFuncionario(){
        Funcionario funcionario = new Funcionario();
        funcionario.setId(Long.valueOf(2));
        funcionario.setCpf("59141628071");
        funcionario.setEmpresa(new Empresa());
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt("123456789"));
        funcionario.setEmail("email@gmail.com");
        funcionario.setNome("Teste Funcionario");
        funcionario.setQtdHorasAlmoco(Float.valueOf(13));
        funcionario.setValorHora(new BigDecimal("12"));
        funcionario.setQtdHorasTrabalhoDia(Float.valueOf(2));
        funcionario.setDataCriacao(new Date());
        funcionario.setDataAtualizacao(new Date());
        return funcionario;
    }

    private String convertToJSON(Funcionario funcionario) throws JsonProcessingException {
        funcionario.setNome("Teste de Alteracao");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(funcionario);
    }
}
