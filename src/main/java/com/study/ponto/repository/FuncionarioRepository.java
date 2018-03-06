package com.study.ponto.repository;

import com.study.ponto.api.entities.Funcionario;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario,Long> {
}
