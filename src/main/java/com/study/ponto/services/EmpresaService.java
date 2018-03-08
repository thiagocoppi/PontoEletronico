package com.study.ponto.services;

import com.study.ponto.api.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {

    Optional<Empresa> buscarPorCnpj(String CNPJ);

    Empresa persistirEmpresa(Empresa empresa);


}
