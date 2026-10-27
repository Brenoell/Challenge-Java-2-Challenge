package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.dao.ContaDAO;
import br.com.fiap.soulupsociety.dto.LoginDTO;
import br.com.fiap.soulupsociety.models.Conta;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
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

    // Verifica se existe uma sessão autenticada
    @GetMapping("/sessao")
    public ResponseEntity<?> verificarSessao(
            HttpServletRequest request) {

        HttpSession sessao = request.getSession(false);

        if (sessao == null ||
                sessao.getAttribute("idConta") == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("autenticado", false));
        }

        return ResponseEntity.ok(
                Map.of(
                        "autenticado", true,
                        "idConta", sessao.getAttribute("idConta")
                )
        );
    }

    // Encerra a sessão
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest request) {

        HttpSession sessao = request.getSession(false);

        if (sessao != null) {
            sessao.invalidate();
        }

        return ResponseEntity.ok(
                Map.of("mensagem", "Logout realizado com sucesso")
        );
    }
}
