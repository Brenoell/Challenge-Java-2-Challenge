package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.models.Usuario;
import br.com.fiap.soulupsociety.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController() {
        usuarioService = new UsuarioService();
    }

    @PostMapping("/criar")
    public ResponseEntity<String> cadastrar(@RequestBody Usuario usuario) {

        try {
            usuarioService.cadastrar(usuario);

            return ResponseEntity.status(HttpStatus.CREATED).body("CADASTRO FEITO!");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CADASTRO NAO CONCLUIDO");
        }
    }

    @GetMapping("/procurarPorId/{id}")
    public Usuario consultarPorId(@PathVariable Integer id) {

        return usuarioService.consultarPorId(id);
    }

    @GetMapping("/procurarPorCpf/{cpf}")
    public Usuario consultarPorCpj(@PathVariable Long cpf){
        return usuarioService.consultarPorCpf(cpf);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletarUsuarioPorId(@PathVariable Integer id){
        usuarioService.deletarUsuario(id);
    }

    @PutMapping("/atualizarUsuario/{id}")
    public void atualizarUsuario(
            @RequestBody Usuario usuario,
            @PathVariable Integer id) {

        usuario.setId(id);

        usuarioService.atualizarUsuario(usuario);
    }

}
