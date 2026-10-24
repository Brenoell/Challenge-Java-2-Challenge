package br.com.fiap.soulupsociety.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class Comentario {

    private Integer id;
    private String textoComentario;
    private LocalDate dataComentario;
    private Integer quantidadeCurtidas;
    private byte[] imagem;
    private Conta conta;
}