package com.study.ponto.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CadastroPJDto {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String razaoSocial;
    private String cnpj;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "O nome não pode ser vazio !")
    @Length(min = 3, max = 200, message = "O nome deve conter entre 3 e 200 caracteres")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotEmpty(message = "O email não pode ser vazio !")
    @Length(min = 5, max = 100, message = "O email deve conter entre 5 até 100 caracteres")
    @Email(message = "Email inválido")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "A senha não pode vazia")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @NotEmpty(message = "O cpf não pode ser vazio")
    @CPF(message = "CPF inválido")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotEmpty(message = "A razão social não pode ser vazia")
    @Length(min = 5,max = 100,message = "A razão social deve conter entre 5 à 100 caracteres")
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @CNPJ(message = "CNPJ inválido")
    @NotEmpty(message = "CNPJ não pode ser vazio")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString(){
        return "Cadastro[id =" + this.getId() + ", nome=" + this.getNome() + ",senha=" + this.getSenha() + ",cpf=" + getCpf() + "razao social=" + this.getRazaoSocial()
                + ",cnpj=" + this.getCnpj();
    }
}
