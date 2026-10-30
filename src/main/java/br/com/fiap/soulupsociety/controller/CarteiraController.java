package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Carteira;
import br.com.fiap.soulupsociety.service.CarteiraService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    private final CarteiraService service = new CarteiraService();

    // CREATE - Cadastrar carteira
    @PostMapping
    public void cadastrar(@RequestBody Carteira carteira) {
        service.cadastrar(carteira);
    }

    // READ - Listar todas as carteiras
    @GetMapping
    public List<Carteira> listar() {
        return service.listar();
    }

    // READ - Consultar carteira por ID
    @GetMapping("/{id}")
    public Carteira consultar(@PathVariable Integer id) {
        return service.consultar(id);
    }

    // UPDATE - Adicionar pontos
    @PatchMapping("/{id}/pontos/adicionar")
    public void adicionarPontos(
            @PathVariable Integer id,
            @RequestParam int quantidade) {
        service.adicionarPontos(id, quantidade);
    }

    // UPDATE - Remover pontos
    @PatchMapping("/{id}/pontos/remover")
    public void removerPontos(
            @PathVariable Integer id,
            @RequestParam int quantidade) {
        service.removerPontos(id, quantidade);
    }

    // UPDATE - Adicionar vales-desconto
    @PatchMapping("/{id}/vales-desconto/adicionar")
    public void adicionarValesDesconto(
            @PathVariable Integer id,
            @RequestParam int quantidade) {
        service.adicionarValesDesconto(id, quantidade);
    }

    // UPDATE - Remover vales-desconto
    @PatchMapping("/{id}/vales-desconto/remover")
    public void removerValesDesconto(
            @PathVariable Integer id,
            @RequestParam int quantidade) {
        service.removerValesDesconto(id, quantidade);
    }

    // UPDATE - Adicionar passagens
    @PatchMapping("/{id}/passagens/adicionar")
    public void adicionarPassagem(
            @PathVariable Integer id,
            @RequestParam int quantidade) {
        service.adicionarPassagem(id, quantidade);
    }

    // DELETE - Deletar carteira
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}