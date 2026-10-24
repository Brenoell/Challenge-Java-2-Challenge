package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.service.ContaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private ContaService service = new ContaService();

    @PostMapping
    public void cadastrar(@RequestBody Conta conta) {
        service.cadastrar(conta);
    }

    @GetMapping
    public List<Conta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Conta consultar(@PathVariable Integer id) {
        return service.consultar(id);
    }

    @PutMapping
    public void atualizar(@RequestBody Conta conta) {
        service.atualizar(conta);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}