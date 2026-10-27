package br.com.fiap.soulupsociety.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Conta {

    private Integer id;
    private String nomeConta;
    private String email;
    private String senha;
    private LocalDate dataCriacao;
    private String bio;

    private Usuario usuario;

    private Carteira carteira;
    private List<Postagem> postagens;
    private List<Comentario> comentarios;
}