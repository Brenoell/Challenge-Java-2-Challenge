package br.com.fiap.soulupsociety.models;


import br.com.fiap.soulupsociety.enums.TipoContaEnum;
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
    private LocalDate dataCriacao;
    private String bio;
    private TipoContaEnum TipoConta;

    private Usuario usuario;
    private Carteira carteira;
    private List<Comentario> comentarios;
    private List<Postagem> postagem;
    private List<Desafio> desafios;
}
