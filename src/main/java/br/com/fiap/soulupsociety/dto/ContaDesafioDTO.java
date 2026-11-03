package br.com.fiap.soulupsociety.dto;

import java.time.LocalDate;

public record ContaDesafioDTO(
        Integer id,
        Integer idConta,
        Integer idDesafio,
        String nomeDesafio,
        int pontosRecebidos,
        LocalDate dataConclusao
) {
}