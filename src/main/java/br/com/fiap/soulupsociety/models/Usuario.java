package br.com.fiap.soulupsociety.models;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Usuario {
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private int numeroCpf;

    private Conta conta;
}
