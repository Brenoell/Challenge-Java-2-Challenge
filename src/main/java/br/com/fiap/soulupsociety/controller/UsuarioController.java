
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
    public List<Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Usuario consultar(@PathVariable Integer id) {
        return service.consultarPorId(id);
    }

    @GetMapping("/procurarPorCpf/{cpf}")
    public ResponseEntity<Usuario> consultarPorCpf(
            @PathVariable Long cpf) {

        Usuario usuario = service.consultarPorCpf(cpf);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @PutMapping
    public void atualizar(@RequestBody Usuario usuario) {
        service.atualizar(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}
