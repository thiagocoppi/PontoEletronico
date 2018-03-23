package com.study.ponto.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.ponto.api.entities.Empresa;
import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.enums.PerfilEnum;
import com.study.ponto.dtos.CadastroPFDto;
import com.study.ponto.dtos.FuncionarioDto;
import com.study.ponto.services.EmpresaService;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles("test")
public class CadastroPFControllerTest {
    @MockBean
    private EmpresaService empresaService;

    @MockBean
    private FuncionarioService funcionarioService;

    @Autowired
    private MockMvc mockMvc;

    private static final String RAZAO_SOCIAL = "Testes Empresa S.a";
    private static final String CNPJ = "99340692000184";
    private static final String URL_BASE = "/ponto/api";


    @Test
    public void cadastrarPFSucessoTest() throws Exception{
        Empresa empresa = obterDadosEmpresa();
        BDDMockito.given(this.empresaService.persistirEmpresa(empresa)).willReturn(empresa);
        CadastroPFDto cadastroPFDto = obterDadosFuncionario(empresa);
        Funcionario funcionario = obterFuncionario(cadastroPFDto, empresa);
        BDDMockito.given(this.funcionarioService.persistir(funcionario)).willReturn(Optional.of(funcionario));

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE + "/" + "pf")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(convertFuncionarioToJson(cadastroPFDto)))
                                                .andExpect(status().isOk())
                                                .andExpect(jsonPath("$.data.email").value("emaildeteste@gmail.com"))
                                                .andExpect(jsonPath("$.data.nome").value("Teste Controller PF"));
    }

    @Test
    public void cadastrarPFFailTest() throws Exception{
        Empresa empresa = obterDadosEmpresa();
        BDDMockito.given(this.empresaService.persistirEmpresa(empresa)).willReturn(empresa);
        CadastroPFDto cadastroPFDto = obterDadosFuncionario(empresa);
        Funcionario funcionario = obterFuncionario(cadastroPFDto,empresa);
        BDDMockito.given(this.funcionarioService.persistir(funcionario)).willReturn(Optional.of(funcionario));
        cadastroPFDto.setCpf("6666");
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE + "/" + "pf")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .content(convertFuncionarioToJson(cadastroPFDto)))
                                               .andExpect(status().isBadRequest());
    }

    private Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(RAZAO_SOCIAL);
        empresa.setCnpj(CNPJ);
        return empresa;
    }

    private CadastroPFDto obterDadosFuncionario(Empresa empresa){
        CadastroPFDto cadastroPFDto = new CadastroPFDto();
        cadastroPFDto.setCnpj("88226007000177");
        cadastroPFDto.setCpf("26749425097");
        cadastroPFDto.setEmail("emaildeteste@gmail.com");
        cadastroPFDto.setNome("Teste Controller PF");
        cadastroPFDto.setRazaoSocial("Razao Social Testadora");
        cadastroPFDto.setSenha("123456789");
        return cadastroPFDto;
    }

    private String convertFuncionarioToJson(CadastroPFDto funcionario) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(funcionario);
    }

    private Funcionario obterFuncionario(CadastroPFDto cadastroPFDto, Empresa empresa){
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail(cadastroPFDto.getEmail());
        funcionario.setCpf(cadastroPFDto.getCpf());
        funcionario.setNome(cadastroPFDto.getNome());
        funcionario.setSenha(cadastroPFDto.getSenha());
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

}
