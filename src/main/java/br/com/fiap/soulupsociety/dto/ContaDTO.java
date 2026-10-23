package br.com.fiap.soulupsociety.dto;

import br.com.fiap.soulupsociety.models.TipoConta;
import java.time.LocalDate;

public record ContaDTO(
        Integer id,
        String nomeConta,
        String email,
        LocalDate dataCriacao,
        String bio,
        TipoConta tipoConta
) {
}