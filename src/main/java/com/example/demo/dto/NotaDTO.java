package com.example.demo.dto;

import java.util.List;

public class NotaDTO {

    private String nome;
    private String endereco;
    private Integer numero;



    List<NotaItemDTO> itens;

    private Double total;

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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<NotaItemDTO> getItens() {
        return itens;
    }

    public void setItens(List<NotaItemDTO> itens) {
        this.itens = itens;
    }
}
