package com.study.ponto.services.implement;

import com.study.ponto.api.entities.Empresa;
import com.study.ponto.repository.EmpresaRepository;
import com.study.ponto.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> buscarPorCnpj(String CNPJ) {
        log.info("Buscando uma empresa por CNPJ: " ,CNPJ);
        return Optional.ofNullable(this.empresaRepository.findByCnpj(CNPJ));
    }

    @Override
    public Empresa persistirEmpresa(Empresa empresa) {
        log.info("Salvando uma empresa no BD: ",empresa);
        return this.empresaRepository.save(empresa);
    }
}
