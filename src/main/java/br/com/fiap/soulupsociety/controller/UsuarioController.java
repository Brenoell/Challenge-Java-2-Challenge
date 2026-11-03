
package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Usuario;
import br.com.fiap.soulupsociety.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service = new UsuarioService();

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(
            @RequestBody Usuario usuario) {

        Usuario usuarioCadastrado = service.cadastrar(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> consultar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(service.consultarPorId(id));
    }

    @GetMapping("/procurarPorCpf/{cpf}")
    public ResponseEntity<Usuario> consultarPorCpf(
            @PathVariable Long cpf) {

        return ResponseEntity.ok(service.consultarPorCpf(cpf));
    }

    @PutMapping
    public ResponseEntity<Void> atualizar(
            @RequestBody Usuario usuario) {

        service.atualizar(usuario);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
