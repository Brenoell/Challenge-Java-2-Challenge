
package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Postagem;
import br.com.fiap.soulupsociety.service.PostagemService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/postagens")
public class PostagemController {

    private final PostagemService service = new PostagemService();

    // CREATE - Cadastrar postagem
    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody Postagem postagem) {

        service.cadastrar(postagem);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    // READ - Listar todas as postagens
    @GetMapping
    public ResponseEntity<List<Postagem>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    // READ - Consultar postagem por ID
    @GetMapping("/{id}")
    public ResponseEntity<Postagem> consultar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(service.consultar(id));
    }

    // UPDATE - Atualizar postagem
    @PutMapping
    public ResponseEntity<Void> atualizar(
            @RequestBody Postagem postagem) {

        service.atualizar(postagem);

        return ResponseEntity.noContent().build();
    }

    // DELETE - Deletar postagem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}