
package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.dto.LoginDTO;
import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.service.ContaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService service = new ContaService();

    // CREATE - Cadastrar conta
    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody Conta conta) {

        service.cadastrar(conta);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<Conta> login(
            @RequestBody LoginDTO login) {

        Conta conta = service.login(
                login.email(),
                login.senha()
        );

        return ResponseEntity.ok(conta);
    }

    // READ - Listar contas
    @GetMapping
    public ResponseEntity<List<Conta>> listar() {

        return ResponseEntity.ok(service.listar());
    }

    // READ - Consultar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Conta> consultar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(service.consultar(id));
    }

    // UPDATE - Atualizar conta
    @PutMapping
    public ResponseEntity<Void> atualizar(
            @RequestBody Conta conta) {

        service.atualizar(conta);

        return ResponseEntity.noContent().build();
    }

    // DELETE - Deletar conta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
