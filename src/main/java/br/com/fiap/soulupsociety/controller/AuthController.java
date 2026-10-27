
package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.dao.ContaDAO;
import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.dto.LoginDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ContaDAO contaDAO = new ContaDAO();

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDTO dados,
            HttpServletRequest request) {

        Conta conta = contaDAO.login(
                dados.email(),
                dados.senha()
        );

        if (conta == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "erro",
                            "E-mail ou senha inválidos"
                    ));
        }

        HttpSession sessao = request.getSession(true);

        sessao.setAttribute("idConta", conta.getId());

        return ResponseEntity.ok(
                Map.of(
                        "mensagem", "Login realizado com sucesso",
                        "idConta", conta.getId(),
                        "nome", conta.getNomeConta()
                )
        );
    }
}
