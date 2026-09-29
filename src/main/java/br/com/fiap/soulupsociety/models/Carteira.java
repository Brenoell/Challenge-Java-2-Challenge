package br.com.fiap.soulupsociety.models;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Carteira {
    private Integer id;
    private int quantidadePontos;
    private int valesDesconto;
    private int quantidadePassagens;

    private Conta conta;
}
