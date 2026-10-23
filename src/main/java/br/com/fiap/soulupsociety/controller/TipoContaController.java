package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.TipoConta;
import br.com.fiap.soulupsociety.service.TipoContaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-conta")
public class TipoContaController {

    private TipoContaService service = new TipoContaService();

    @PostMapping
    public void cadastrar(@RequestBody TipoConta tipoConta) {
        service.cadastrar(tipoConta);
    }

    @GetMapping
    public List<TipoConta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public TipoConta consultar(@PathVariable Integer id) {
        return service.consultar(id);
    }

    @PutMapping
    public void atualizar(@RequestBody TipoConta tipoConta) {
        service.atualizar(tipoConta);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}