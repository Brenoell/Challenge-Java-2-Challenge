package br.com.fiap.soulupsociety.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Desafio {

    private Integer id;
    private String nome;
    private String descricaoDesafio;
    private Integer qtdPontos;
}