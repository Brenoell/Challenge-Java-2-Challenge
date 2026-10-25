package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Usuario;
import br.com.fiap.soulupsociety.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service = new UsuarioService();

    @PostMapping
    public void cadastrar(@RequestBody Usuario usuario) {
        service.cadastrar(usuario);
    }

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Usuario consultar(@PathVariable Integer id) {
        return service.consultarPorId(id);
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