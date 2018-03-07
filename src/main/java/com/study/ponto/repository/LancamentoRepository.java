package com.study.ponto.repository;

import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Repository
@Transactional
@NamedQueries({
        @NamedQuery(name = "LancamentoRepository.findFuncionarioById",
                    query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId")})

public interface LancamentoRepository extends JpaRepository<Lancamento,Long>{

    Lancamento findFuncionarioById(@Param("funcionarioId") Long funcionarioId);

    Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
