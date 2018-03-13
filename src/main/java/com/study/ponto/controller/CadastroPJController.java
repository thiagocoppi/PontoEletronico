package com.study.ponto.controller;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.enums.PerfilEnum;
import com.study.ponto.dtos.CadastroPJDto;
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

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "/ponto/api")
@CrossOrigin(origins = "*")
public class CadastroPJController {
    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private FuncionarioService funcionarioService;

    public CadastroPJController(){}

    @RequestMapping(path = "/PJ",method = RequestMethod.POST)
    public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto, BindingResult bindingResult) throws NoSuchAlgorithmException{
        log.info("Cadastrando PJ : {}",cadastroPJDto);

        Response<CadastroPJDto> response = new Response<>();
        validarDadosExistentes(cadastroPJDto,bindingResult);

        Empresa empresa = this.converterDadosEmpresa(cadastroPJDto);
        Funcionario funcionario = this.converterDadosFuncionario(cadastroPJDto,empresa);

        if(bindingResult.hasErrors()){
            log.info("Erro no cadastro de PJ",bindingResult.getAllErrors());
            bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        this.empresaService.persistirEmpresa(empresa);
        funcionario.setEmpresa(empresa);
        this.funcionarioService.persistir(funcionario);

        response.setData(converterDTO(funcionario));
        return ResponseEntity.ok(response);
    }

    private void validarDadosExistentes(CadastroPJDto cadastroPJDto,BindingResult bindingResult){
        this.empresaService.buscarPorCnpj(cadastroPJDto.getCnpj()).ifPresent(emp -> bindingResult.addError(new ObjectError("empresa","Empresa já existente")));

        this.funcionarioService.findByCpf(cadastroPJDto.getCpf()).ifPresent(func -> bindingResult.addError(new ObjectError("funcionario", "CPF já existente")));

        this.funcionarioService.findByEmailOrCpf(cadastroPJDto.getEmail(),cadastroPJDto.getCpf()).ifPresent(func -> bindingResult.addError(new ObjectError("funcionario","Email e CPF já existente")));
    }

    private Empresa converterDadosEmpresa(CadastroPJDto cadastroPJDto){
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPJDto.getCnpj());
        empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
        return empresa;
    }

    private Funcionario converterDadosFuncionario(CadastroPJDto cadastroPJDto, Empresa empresa){
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
        return funcionario;
    }

    private CadastroPJDto converterDTO(Funcionario funcionario){
        CadastroPJDto cadastroPJDto = new CadastroPJDto();
        cadastroPJDto.setId(funcionario.getId());
        cadastroPJDto.setNome(funcionario.getNome());
        cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());
        cadastroPJDto.setCpf(funcionario.getCpf());
        cadastroPJDto.setEmail(funcionario.getEmail());
        cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        cadastroPJDto.setSenha(funcionario.getSenha());
        return  cadastroPJDto;
    }
}
