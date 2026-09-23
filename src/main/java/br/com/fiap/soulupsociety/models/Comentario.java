package br.com.fiap.soulupsociety.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Comentario {
    private Integer id;
    private String textoComentario;
    private LocalDate dataComentario;
    private int quantidadeCompartilhamentos;

    private Conta conta;
}
