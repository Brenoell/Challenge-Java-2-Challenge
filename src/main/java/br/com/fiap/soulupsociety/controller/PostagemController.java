package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Postagem;
import br.com.fiap.soulupsociety.service.PostagemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/postagens")
public class PostagemController {

    private final PostagemService service = new PostagemService();

    @PostMapping
    public void cadastrar(@RequestBody Postagem postagem) {
        service.cadastrar(postagem);
    }

    @GetMapping
    public List<Postagem> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Postagem consultar(@PathVariable Integer id) {
        return service.consultar(id);
    }

    @PutMapping
    public void atualizar(@RequestBody Postagem postagem) {
        service.atualizar(postagem);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}