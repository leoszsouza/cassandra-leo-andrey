package com.example.demo.dto;

public class NotaItemDTO {

    private String descricaoServico;
    private String quantidade;
    private Long valorUnitario;
    private String recurso;
    private String funcao;
    private Double porcentoTaxa;
    private Double porcentoDesconto;
    private Double subtotal;

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public Long getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Long valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Double getPorcentoTaxa() {
        return porcentoTaxa;
    }

    public void setPorcentoTaxa(Double porcentoTaxa) {
        this.porcentoTaxa = porcentoTaxa;
    }

    public Double getPorcentoDesconto() {
        return porcentoDesconto;
    }

    public void setPorcentoDesconto(Double porcentoDesconto) {
        this.porcentoDesconto = porcentoDesconto;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

}
