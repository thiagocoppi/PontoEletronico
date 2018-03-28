package com.study.ponto.services.implement;

import com.study.ponto.api.entities.Funcionario;
import com.study.ponto.dtos.FuncionarioDto;
import com.study.ponto.repository.FuncionarioRepository;
import com.study.ponto.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Optional<Funcionario> findByCpf(String cpf) {
        return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Funcionario> findByEmail(String email) {
        return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
    }

    @Override
    public Optional<Funcionario> findByEmailOrCpf(String email, String CPF) {
        return Optional.ofNullable(this.funcionarioRepository.findByCpfOrEmail(email,CPF));
    }

    @Override
    public Optional<Funcionario> findById(long id) {
        return Optional.ofNullable(this.funcionarioRepository.findById(id));
    }

    @Override
    public Optional<Funcionario> persistir(Funcionario funcionario) {
        return Optional.ofNullable(this.funcionarioRepository.save(funcionario));
    }

    @Override
    public Funcionario converterFuncionarioDTO(Funcionario funcionarioDto) {

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
