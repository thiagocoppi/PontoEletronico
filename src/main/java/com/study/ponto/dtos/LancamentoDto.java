package com.study.ponto.dtos;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class LancamentoDto {
    private Long id;
    private String data;
    private String tipo;
    private String descricao;
    private String localizacao;
    private Long funcionarioId;

    public LancamentoDto(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @NotEmpty(message = "A data não pode ser vazia!")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    @Override
    public String toString(){
        return "LancamentoDto [id=" + id + ",data =" + data + ",descricao="+descricao+",localizacao="+localizacao+"funcionarioId="+funcionarioId +"]";
    }

}
