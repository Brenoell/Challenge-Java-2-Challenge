package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Comentario;
import br.com.fiap.soulupsociety.service.ComentarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private ComentarioService service = new ComentarioService();

    @PostMapping
    public void cadastrar(@RequestBody Comentario comentario) {
        service.cadastrar(comentario);
    }

    @GetMapping
    public List<Comentario> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Comentario consultar(@PathVariable Integer id) {
        return service.consultar(id);
    }

    @PutMapping
    public void atualizar(@RequestBody Comentario comentario) {
        service.atualizar(comentario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}