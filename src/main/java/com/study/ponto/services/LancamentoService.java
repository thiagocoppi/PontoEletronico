package com.study.ponto.services;

import com.study.ponto.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LancamentoService {
    Page<Lancamento> findFuncionarioById(long id, PageRequest pageable);

    Optional<Lancamento> findById(long id);

    Lancamento persistir(Lancamento lancamento);

    void remover(long id);
}
