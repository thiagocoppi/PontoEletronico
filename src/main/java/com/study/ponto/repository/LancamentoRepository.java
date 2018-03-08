package com.study.ponto.repository;

import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

@Repository
@Transactional
/*@NamedQueries({
        @NamedQuery(name = "LancamentoRepository.findFuncionarioById",
                    query = "SELECT lanc FROM Lancamento lanc WHERE lanc.Funcionario.Id = :funcionarioId")})*/

public interface LancamentoRepository extends JpaRepository<Lancamento,Long>{
    @Query(value = "select u from Lancamento u where u.funcionario.id = :funcionarioId")
    List<Lancamento> findFuncionarioById(@Param("funcionarioId") Long funcionarioId);
    @Query(value = "select lanc from Lancamento lanc where lanc.funcionario.id = :funcionarioId")
    Page<Lancamento> findFuncionarioById(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
