package br.com.fiap.soulupsociety.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ContaDesafio {

    private Integer id;
    private Conta conta;
    private Desafio desafio;
    private LocalDate dataConclusao;
    private Integer pontosRecebidos;
}