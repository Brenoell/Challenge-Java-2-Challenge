package br.com.fiap.soulupsociety.models;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Postagem {
    private Integer id;
    private String descricaoTexto;
    private int quantidadeCurtidas;
    private int compartilhamentos;

    private Conta conta;
}
