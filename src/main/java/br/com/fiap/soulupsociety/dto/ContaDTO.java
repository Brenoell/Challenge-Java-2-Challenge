package br.com.fiap.soulupsociety.dto;

import java.time.LocalDate;

public record ContaDTO(
        Integer id,
        String nomeConta,
        String email,
        String senha,
        LocalDate dataCriacao,
        String bio
) {
}