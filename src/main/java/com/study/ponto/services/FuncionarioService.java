package com.study.ponto.services;

import com.study.ponto.api.entities.Funcionario;

import java.util.Optional;

public interface FuncionarioService {
    Optional<Funcionario> findByCpf(String cpf);

    Optional<Funcionario> findByEmail(String email);

    Optional<Funcionario> findByEmailOrCpf(String email, String CPF);

    Optional<Funcionario> findById(long id);

    Optional<Funcionario> persistir(Funcionario funcionario);
}
