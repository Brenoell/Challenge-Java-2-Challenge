
        package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.dto.ContaDesafioDTO;
import br.com.fiap.soulupsociety.mapper.ContaDesafioMapper;
import br.com.fiap.soulupsociety.models.ContaDesafio;
import br.com.fiap.soulupsociety.service.ContaDesafioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas/{idConta}/desafios")
public class ContaDesafioController {

    private final ContaDesafioService service = new ContaDesafioService();

    // TESTE - Verificar se a rota está funcionando
    @GetMapping("/teste")
    public ResponseEntity<String> teste(
            @PathVariable Integer idConta) {

        return ResponseEntity.ok("Conta recebida: " + idConta);
    }

    // CREATE - Concluir desafio (registra e credita os pontos)
    @PostMapping("/{idDesafio}/concluir")
    public ResponseEntity<ContaDesafioDTO> concluir(
            @PathVariable Integer idConta,
            @PathVariable Integer idDesafio) {

        ContaDesafio conclusao = service.concluir(idConta, idDesafio);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ContaDesafioMapper.toRecordDTO(conclusao));
    }

    // READ - Listar desafios concluídos pela conta
    @GetMapping
    public ResponseEntity<List<ContaDesafioDTO>> listar(
            @PathVariable Integer idConta) {

        List<ContaDesafioDTO> conclusoes = service.listarPorConta(idConta)
                .stream()
                .map(ContaDesafioMapper::toRecordDTO)
                .toList();

        return ResponseEntity.ok(conclusoes);
    }
}
