package com.study.ponto.services.implement;

import com.study.ponto.api.entities.Lancamento;
import com.study.ponto.repository.LancamentoRepository;
import com.study.ponto.services.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LancamentoServiceImpl implements LancamentoService {
    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Page<Lancamento> findFuncionarioById(long id, PageRequest pageable) {
        return this.lancamentoRepository.findFuncionarioById(id,pageable);
    }

    @Override
    public List<Lancamento> findFuncionarioById(long id) {
        return this.lancamentoRepository.findFuncionarioById(id);
    }

    @Override
    public Optional<Lancamento> findById(long id) {
        return this.lancamentoRepository.findById(id);
    }

    @Override
    public Lancamento persistir(Lancamento lancamento) {
        return this.lancamentoRepository.save(lancamento);
    }

    @Override
    public void remover(long id) {
        this.lancamentoRepository.deleteById(id);
    }
}
