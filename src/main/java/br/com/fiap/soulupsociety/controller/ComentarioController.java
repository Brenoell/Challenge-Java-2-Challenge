
package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Comentario;
import br.com.fiap.soulupsociety.service.ComentarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService service = new ComentarioService();

    // CREATE - Cadastrar comentário
    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody Comentario comentario) {

        service.cadastrar(comentario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    // READ - Listar todos os comentários
    @GetMapping
    public ResponseEntity<List<Comentario>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    // READ - Listar comentários de uma postagem
    @GetMapping("/postagem/{idPostagem}")
    public ResponseEntity<List<Comentario>> listarPorPostagem(
            @PathVariable Integer idPostagem) {

        return ResponseEntity.ok(
                service.listarPorPostagem(idPostagem)
        );
    }

    // READ - Consultar comentário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> consultar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(service.consultar(id));
    }

    // UPDATE - Atualizar comentário
    @PutMapping
    public ResponseEntity<Void> atualizar(
            @RequestBody Comentario comentario) {

        service.atualizar(comentario);

        return ResponseEntity.noContent().build();
    }

    // DELETE - Deletar comentário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
