package com.study.ponto.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.ponto.api.entities.Empresa;
import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.enums.PerfilEnum;
import com.study.ponto.dtos.CadastroPFDto;
import com.study.ponto.dtos.CadastroPJDto;
import com.study.ponto.services.EmpresaService;
import com.study.ponto.services.FuncionarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@ActiveProfiles("test")
public class CadastroPJControllerTest {

    private static final String RAZAO_SOCIAL = "Testes Unitarios PJ";
    private static final String CNPJ = "82590349000195";
    private static final String URL_BASE = "/ponto/api";
    @MockBean
    private EmpresaService empresaService;
    @MockBean
    private FuncionarioService funcionarioService;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCadastrarPJSucess() throws Exception{
        Empresa empresa = obterDadosEmpresa();
        BDDMockito.given(this.empresaService.persistirEmpresa(empresa)).willReturn(empresa);
        CadastroPJDto cadastroPJDto = obterDadosFuncionario(empresa);
        Funcionario funcionario = obterFuncionario(cadastroPJDto,empresa);
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE + "/" + "PJ")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(convertDTOToJson(cadastroPJDto)))
                                                .andExpect(status().isOk())
                                                .andExpect(jsonPath("$.data.cpf").value("26749425097"))
                                                .andExpect(jsonPath("$.data.nome").value("Teste Controller PJ"));
    }

    @Test
    public void testCadastrarPJFail() throws Exception{
        Empresa empresa = obterDadosEmpresa();
        BDDMockito.given(this.empresaService.persistirEmpresa(empresa)).willReturn(empresa);
        CadastroPJDto cadastroPJDto = obterDadosFuncionario(empresa);
        cadastroPJDto.setCpf("5555");
        Funcionario funcionario = obterFuncionario(cadastroPJDto,empresa);
        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE+"/PJ")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .content(convertDTOToJson(cadastroPJDto)))
                                               .andExpect(status().isBadRequest());

    }

    private Empresa obterDadosEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(RAZAO_SOCIAL);
        empresa.setCnpj(CNPJ);
        return empresa;
    }

    private Funcionario obterFuncionario(CadastroPJDto cadastroPJDto, Empresa empresa){
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setSenha(cadastroPJDto.getSenha());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

    private CadastroPJDto obterDadosFuncionario(Empresa empresa){
        CadastroPJDto cadastroPJDto = new CadastroPJDto();
        cadastroPJDto.setCnpj("88226007000177");
        cadastroPJDto.setCpf("26749425097");
        cadastroPJDto.setEmail("emaildeteste@gmail.com");
        cadastroPJDto.setNome("Teste Controller PJ");
        cadastroPJDto.setRazaoSocial("Razao Social Testadora");
        cadastroPJDto.setSenha("123456789");
        return cadastroPJDto;
    }

    private String convertDTOToJson(CadastroPJDto cadastroPJDto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(cadastroPJDto);
    }
}
