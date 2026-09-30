package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.service.ContaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private ContaService contaService;

    public ContaController() {
        contaService = new ContaService();
    }


    // Cadastrar conta
    @PostMapping
    public void cadastrarConta(@RequestBody Conta conta) {
        contaService.cadastrarConta(conta);
    }


    // Consultar conta pelo ID
    @GetMapping("/{id}")
    public Conta consultarPorId(@PathVariable Integer id) {
        return contaService.consultarPorId(id);
    }


    // Consultar conta pelo e-mail
    @GetMapping("/email/{email}")
    public Conta consultarPorEmail(@PathVariable String email) {
        return contaService.consultarPorEmail(email);
    }


    // Atualizar conta
    @PutMapping
    public void atualizarConta(@RequestBody Conta conta) {
        contaService.atualizarConta(conta);
    }


    // Deletar conta
    @DeleteMapping("/{id}")
    public void deletarConta(@PathVariable Integer id) {
        contaService.deletarConta(id);
    }
}