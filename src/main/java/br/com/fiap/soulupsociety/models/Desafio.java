package br.com.fiap.soulupsociety.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Desafio {
    private Integer id;
    private String nome;
    private String descricaoDesafio;
    private int qtdPontos;

    private List<Conta> contas;
}
