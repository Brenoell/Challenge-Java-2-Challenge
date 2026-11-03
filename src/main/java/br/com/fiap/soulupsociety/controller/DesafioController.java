
package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Desafio;
import br.com.fiap.soulupsociety.service.DesafioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/desafios")
public class DesafioController {

    private final DesafioService service = new DesafioService();

    // POST - Cadastrar desafio
    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrar(
            @RequestBody Desafio desafio) {

        service.cadastrar(desafio);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    // GET - Listar todos os desafios
    @GetMapping
    public ResponseEntity<List<Desafio>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    // GET - Consultar desafio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Desafio> consultar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(service.consultar(id));
    }

    // PUT - Atualizar desafio
    @PutMapping
    public ResponseEntity<Void> atualizar(
            @RequestBody Desafio desafio) {

        service.atualizar(desafio);

        return ResponseEntity.noContent().build();
    }

    // DELETE - Excluir desafio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
