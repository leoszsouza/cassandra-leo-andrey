package com.example.demo.dto;

public class NotaMySQLDTO {

    private String nome;
    private String endereco;
    private Integer numero;
    private String descricaoServico;
    private String quantidade;
    private String valorUnitario;
    private String recurso;
    private String funcao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    private Double porcentoTaxa;
    private Double porcentoDesconto;
    private Double subtotal;
    private Double total;


}
