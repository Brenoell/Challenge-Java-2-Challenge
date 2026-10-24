package br.com.fiap.soulupsociety.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class Conta {

    private Integer id;
    private String nomeConta;
    private String email;
    private LocalDate dataCriacao;
    private String bio;
    private Usuario usuario;
    private TipoConta tipoConta;
}