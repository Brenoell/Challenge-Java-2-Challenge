package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Desafio;
import br.com.fiap.soulupsociety.service.DesafioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/desafios")
public class DesafioController {

    private DesafioService service = new DesafioService();

    @PostMapping
    public void cadastrar(@RequestBody Desafio desafio) {
        service.cadastrar(desafio);
    }

    @GetMapping
    public List<Desafio> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Desafio consultar(@PathVariable Integer id) {
        return service.consultar(id);
    }

    @PutMapping
    public void atualizar(@RequestBody Desafio desafio) {
        service.atualizar(desafio);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}