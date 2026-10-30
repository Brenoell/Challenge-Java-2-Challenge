package br.com.fiap.soulupsociety.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Carteira {

    private Integer id;
    private Integer quantidadePontos;
    private Integer valesDesconto;
    private Integer quantidadePassagens;
    private Conta conta;
}