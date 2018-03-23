package com.study.ponto.controllers;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.repository.EmpresaRepository;
import com.study.ponto.services.EmpresaService;
import com.study.ponto.services.FuncionarioService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles("test")
public class EmpresaControllerTest {

    @MockBean
    private EmpresaService empresaService;

    @MockBean
    private FuncionarioService funcionarioService;

    @Autowired
    MockMvc mockMvc;


    private static final String CNPJ = "59680144000189";
    private static final String RAZAO_SOCIAL = "Empresa de Test S.A";
    private static final Long idEmpresa = 1L;
    private static final String URL_BASE = "/ponto/api/empresa";


    @Test
    public void testBuscarPorCNPJInexistente() throws Exception{
        BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + "80767793000108")
                                                 .contentType(MediaType.APPLICATION_JSON))
                                                 .andExpect(status().isBadRequest())
                                                 .andExpect(jsonPath("$.erros").value("Empresa não encontrada com o CNPJ 80767793000108"));
    }

    @Test
    public void testBuscarCNPJValido() throws Exception{
        Empresa empresa = obterDadosEmpresa();
        BDDMockito.given(this.empresaService.buscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(empresa));
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + "/" + CNPJ)
                                                .contentType(MediaType.APPLICATION_JSON))
                                                .andExpect(status().isOk())
                                                .andExpect(jsonPath("$.data.cnpj").value(CNPJ))
                                                .andExpect(jsonPath("$.data.razaoSocial").value(RAZAO_SOCIAL));

    }

    private Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(RAZAO_SOCIAL);
        empresa.setCnpj(CNPJ);
        return empresa;
    }
}
