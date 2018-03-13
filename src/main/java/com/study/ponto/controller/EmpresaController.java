package com.study.ponto.controller;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.dtos.EmpresaDto;
import com.study.ponto.response.Response;
import com.study.ponto.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/ponto/api/empresa")
@CrossOrigin(origins = "*")
public class EmpresaController {
    private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService empresaService;

    @RequestMapping(path = "/{cnpj}", method = RequestMethod.GET)
    private ResponseEntity<Response<Empresa>> buscarPorCNPJ(@PathVariable("cnpj") String CNPJ){
        log.info("Buscando Empresa por CNPJ",CNPJ);
        Response<Empresa> response = new Response<>();
        Optional<Empresa> empresaDto = this.empresaService.buscarPorCnpj(CNPJ);
        if(!empresaDto.isPresent()){
            response.getErros().add("Empresa não encontrada com o CNPJ " + CNPJ);
            return ResponseEntity.badRequest().body(response);
        }
        Empresa empresa = this.converterEmpresaDto(empresaDto.get());
        response.setData(empresa);
        return ResponseEntity.ok(response);
    }

    private Empresa converterEmpresaDto(Empresa empresaDto){
        Empresa empresa = new Empresa();
        empresa.setId(empresaDto.getId());
        empresa.setCnpj(empresaDto.getCnpj());
        empresa.setRazaoSocial(empresaDto.getRazaoSocial());
        empresa.setDataAtualizacao(empresaDto.getDataAtualizacao());
        empresa.setDataCriacao(empresaDto.getDataCriacao());
        return empresa;
    }
}
