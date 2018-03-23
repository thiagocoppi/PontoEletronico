package com.study.ponto.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Optional;

public class CadastroPFDto {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String razaoSocial;
    private String cnpj;
    private BigDecimal qtdValorHora;
    private Float qtdHorasAlmoco;
    private Float qtdHorasTrabalhoDia;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "O nome não pode ser vazio")
    @Length(min = 3, max = 100,message = "O nome deve conter entre 3 à 100 caracteres")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotEmpty(message = "O email não pode ser vazio")
    @Email(message = "Email incorreto")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "A senha não pode ser vazia")
    @Length(min = 8, message = "Sua senha deve conter 8 dígitos")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @NotEmpty(message = "CPF é obrigatório")
    @CPF(message = "CPF Incorreto")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotEmpty(message = "Razao Social é obrigatório")
    @Length(min = 5, max = 100,message = "Campo razao social deve ter entre 3 à 100 caracteres")
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @NotEmpty(message = "CNPJ é obrigatório")
    @CNPJ(message = "CNPJ inválido")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public BigDecimal getQtdValorHora() {
        return qtdValorHora;
    }

    public void setQtdValorHora(BigDecimal qtdValorHora) {
        this.qtdValorHora = qtdValorHora;
    }

    public Float getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    public Float getQtdHorasTrabalhoDia() {
        return qtdHorasTrabalhoDia;
    }

    public void setQtdHorasTrabalhoDia(Float qtdHorasTrabalhoDia) {
        this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
    }

    @Override
    public String toString() {
        return "CadastroPFDto:[id=" + this.getId() + ",nome=" + this.getNome() + ",email=" + this.getEmail() + ",cpf="+this.getCpf()
                + "razaosocial=" + this.getRazaoSocial() + ",cnpj="+this.getCnpj()+"]";
    }
}
