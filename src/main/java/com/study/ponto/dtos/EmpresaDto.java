package com.study.ponto.dtos;



import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotEmpty;

public class EmpresaDto {
    private Long id;
    private String razaoSocial;
    private String CNPJ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @NotEmpty(message = "A razão social é obrigatória !")
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    @CNPJ(message = "CNPJ Inválido")
    @NotEmpty(message = "CNPJ é obrigatório")
    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }
}
