package com.study.ponto.controller;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.enums.PerfilEnum;
import com.study.ponto.dtos.CadastroPFDto;
import com.study.ponto.response.Response;
import com.study.ponto.services.EmpresaService;
import com.study.ponto.services.FuncionarioService;
import com.study.ponto.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/ponto/api")
@CrossOrigin(origins = "*")
public class CadastroPFController {
    private static final Logger log = LoggerFactory.getLogger(CadastroPFController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    @RequestMapping(path = "/pf", method = RequestMethod.POST)
    public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPFDto, BindingResult bindingResult){
        log.info("Iniciando o cadastro de uma pessoa física:{}", cadastroPFDto);
        Response<CadastroPFDto> response = new Response<>();
        this.validarDados(cadastroPFDto,bindingResult);

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
            log.info("Encontrado erros ao cadastrar uma pessoa",response.getErros());
            return ResponseEntity.badRequest().body(response);
        }
        Empresa empresa = this.converterDadosEmpresa(cadastroPFDto);
        this.empresaService.persistirEmpresa(empresa);
        Funcionario funcionario = this.converterDadosFuncionario(cadastroPFDto, empresa);
        this.funcionarioService.persistir(funcionario);
        response.setData(cadastroPFDto);
        return ResponseEntity.ok(response);
    }

    private Empresa converterDadosEmpresa(CadastroPFDto cadastroPFDto){
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPFDto.getCnpj());
        empresa.setRazaoSocial(cadastroPFDto.getRazaoSocial());
        return empresa;
    }

    private Funcionario converterDadosFuncionario(CadastroPFDto cadastroPFDto, Empresa empresa){
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPFDto.getNome());
        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDto.getSenha()));
        funcionario.setEmail(cadastroPFDto.getEmail());
        funcionario.setCpf(cadastroPFDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setQtdHorasTrabalhoDia(cadastroPFDto.getQtdHorasTrabalhoDia());
        funcionario.setQtdHorasAlmoco(cadastroPFDto.getQtdHorasAlmoco());
        funcionario.setValorHora(cadastroPFDto.getQtdValorHora());
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

    private void validarDados(CadastroPFDto cadastroPFDto, BindingResult bindingResult){
        this.empresaService.buscarPorCnpj(cadastroPFDto.getCnpj()).ifPresent(erro -> bindingResult.addError(new ObjectError("empresa", "Empresa com o mesmo CNPJ")));
        this.funcionarioService.findByCpf(cadastroPFDto.getCpf()).ifPresent(erro -> bindingResult.addError(new ObjectError("funcionario","Funcionário com o mesmo CPF")));
        this.funcionarioService.findByEmail(cadastroPFDto.getEmail()).ifPresent(erro -> bindingResult.addError(new ObjectError("funcionario","Funcionario com o mesmo email")));
    }


}
