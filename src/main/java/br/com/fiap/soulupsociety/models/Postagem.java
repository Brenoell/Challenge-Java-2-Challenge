package br.com.fiap.soulupsociety.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Postagem {

    private Integer id;
    private String descricaoTexto;
    private LocalDate dataPostagem;
    private Integer quantidadeCurtidas;
    private byte[] imagem;
    private Conta conta;
    private List<Comentario> comentarios;
}