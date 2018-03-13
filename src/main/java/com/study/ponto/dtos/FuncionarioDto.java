package com.study.ponto.dtos;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Optional;

public class FuncionarioDto {
    private Long id;
    private String email;
    private String CPF;
    private String nome;
    private Optional<String> senha = Optional.empty();
    private Optional<BigDecimal> valorHora = Optional.empty();
    private Optional<Float> qtdHorasTrabalho = Optional.empty();
    private Optional<Float> qtdHorasAlmoco = Optional.empty();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "O CPF é obrigatório")
    @CPF(message = "CPF Inválido")
    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Optional<String> getSenha() {
        return senha;
    }

    public void setSenha(Optional<String> senha) {
        this.senha = senha;
    }

    public Optional<BigDecimal> getValorHora() {
        return valorHora;
    }

    public void setValorHora(Optional<BigDecimal> valorHora) {
        this.valorHora = valorHora;
    }

    public Optional<Float> getQtdHorasTrabalho() {
        return qtdHorasTrabalho;
    }

    public void setQtdHorasTrabalho(Optional<Float> qtdHorasTrabalho) {
        this.qtdHorasTrabalho = qtdHorasTrabalho;
    }

    public Optional<Float> getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Optional<Float> qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

}
