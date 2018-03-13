package com.study.ponto.controller;

import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.dtos.FuncionarioDto;
import com.study.ponto.response.Response;
import com.study.ponto.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if(!funcionario.isPresent()){
            log.info("Procurando um funcionário com o cpf{}",cpf);
            response.getErros().add("Funcionário não foi encontrado com o cpf " + cpf);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(converterFuncionarioDTO(funcionario.get()));
        return ResponseEntity.ok(response);
    }


    private Funcionario converterFuncionarioDTO(Funcionario funcionarioDto){
        Funcionario funcionario = new Funcionario();
        funcionario.setId(funcionarioDto.getId());
        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setPerfil(funcionarioDto.getPerfil());
        funcionario.setEmpresa(funcionarioDto.getEmpresa());
        funcionario.setSenha(funcionarioDto.getSenha());
        funcionario.setDataAtualizacao(funcionarioDto.getDataAtualizacao());
        funcionario.setDataCriacao(funcionarioDto.getDataCriacao());
        funcionario.setEmail(funcionarioDto.getEmail());
        funcionario.setCpf(funcionarioDto.getCpf());
        funcionario.setValorHora(funcionarioDto.getValorHora());
        funcionario.setQtdHorasAlmoco(funcionarioDto.getQtdHorasAlmoco());
        funcionario.setQtdHorasTrabalhoDia(funcionarioDto.getQtdHorasTrabalhoDia());
        return funcionario;
    }
}
