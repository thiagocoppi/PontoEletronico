package com.study.ponto.controller;

import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.dtos.FuncionarioDto;
import com.study.ponto.response.Response;
import com.study.ponto.services.FuncionarioService;
import com.study.ponto.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/ponto/api")
public class FuncionarioController {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    public FuncionarioController(){}

    @RequestMapping(path = "/funcionario/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<Response<Funcionario>> verifyExistsByCpf(@PathVariable("cpf") String cpf){
        Optional<Funcionario> funcionario = this.funcionarioService.findByCpf(cpf);
        Response<Funcionario> response = new Response<>();
        log.info("Procurando um funcionário com o cpf{}",cpf);
        if(!funcionario.isPresent()){
            response.getErros().add("Funcionário não foi encontrado com o cpf " + cpf);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(this.funcionarioService.converterFuncionarioDTO(funcionario.get()));
        return ResponseEntity.ok(response);
    }
    @RequestMapping(path = "/funcionario/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response<Funcionario>> updateFuncionario(@PathVariable("id") long id, @Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult bindingResult){
        Response<Funcionario> response = new Response<>();
        Optional<Funcionario> funcionarioOptional = this.funcionarioService.findById(id);
        log.info("Buscando funcionário com o id " + String.valueOf(id));
        if(!funcionarioOptional.isPresent()){
            response.getErros().add("Funcionário não existente com o id "+ String.valueOf(id));
            return ResponseEntity.badRequest().body(response);
        }
        Funcionario funcionario = this.atualizarFuncionario(funcionarioOptional.get(),funcionarioDto,bindingResult);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(erro -> response.getErros().add(erro.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.funcionarioService.persistir(funcionario);
        response.setData(funcionario);

        return ResponseEntity.ok(response);
    }

    private Funcionario atualizarFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult result){
        funcionario.setNome(funcionarioDto.getNome());
        if(!funcionario.getEmail().equals(funcionarioDto.getEmail())){
            this.funcionarioService.findByEmail(funcionarioDto.getEmail()).ifPresent(erro -> result.addError(new ObjectError("email","Email já cadastrado")));
            funcionario.setEmail(funcionarioDto.getEmail());
        }

        if(!funcionario.getCpf().equals(funcionarioDto.getCPF())){
            this.funcionarioService.findByCpf(funcionarioDto.getCPF()).ifPresent(erro -> result.addError(new ObjectError("cpf", "CPF já cadastrado")));
            funcionario.setCpf(funcionarioDto.getCPF());
        }
        funcionario.setQtdHorasTrabalhoDia(funcionarioDto.getQtdHorasTrabalho().get());
        funcionario.setQtdHorasAlmoco(funcionarioDto.getQtdHorasAlmoco().get());
        funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha()));
        return funcionario;
    }


}
