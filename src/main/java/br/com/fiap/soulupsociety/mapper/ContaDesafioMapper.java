package br.com.fiap.soulupsociety.mapper;

import br.com.fiap.soulupsociety.dto.ContaDesafioDTO;
import br.com.fiap.soulupsociety.models.ContaDesafio;

public class ContaDesafioMapper {

    public static ContaDesafioDTO toRecordDTO(ContaDesafio conclusao) {

        return new ContaDesafioDTO(
                conclusao.getId(),
                conclusao.getConta().getId(),
                conclusao.getDesafio().getId(),
                conclusao.getDesafio().getNome(),
                conclusao.getPontosRecebidos(),
                conclusao.getDataConclusao()
        );
    }
}