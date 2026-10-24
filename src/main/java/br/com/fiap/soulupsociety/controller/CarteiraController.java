package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Carteira;
import br.com.fiap.soulupsociety.service.CarteiraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    private CarteiraService service = new CarteiraService();

    @PostMapping
    public void cadastrar(@RequestBody Carteira carteira) {
        service.cadastrar(carteira);
    }

    @GetMapping
    public List<Carteira> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Carteira consultar(@PathVariable Integer id) {
        return service.consultar(id);
    }

    @PutMapping
    public void atualizar(@RequestBody Carteira carteira) {
        service.atualizar(carteira);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}