package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.dto.LoginDTO;
import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.service.ContaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService service = new ContaService();

    @PostMapping
    public void cadastrar(@RequestBody Conta conta) {
        service.cadastrar(conta);
    }

    @PostMapping("/login")
    public Conta login(@RequestBody LoginDTO login) {
        return service.login(login.email(), login.senha());
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