package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.dao.CarteiraDAO;
import br.com.fiap.soulupsociety.dao.ContaDAO;
import br.com.fiap.soulupsociety.dto.LoginDTO;
import br.com.fiap.soulupsociety.models.Carteira;
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
    private final CarteiraDAO carteiraDAO = new CarteiraDAO();

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

    // Dados da conta logada: nome, e-mail, pontos e passagens
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {

        HttpSession sessao = request.getSession(false);

        if (sessao == null ||
                sessao.getAttribute("idConta") == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("autenticado", false));
        }

        Integer idConta = (Integer) sessao.getAttribute("idConta");

        Conta conta = contaDAO.consultarPorId(idConta);

        if (conta == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("erro", "Conta não encontrada"));
        }

        Carteira carteira = carteiraDAO.consultarPorConta(idConta);

        int pontos = carteira != null ? carteira.getQuantidadePontos() : 0;
        int passagens = carteira != null ? carteira.getQuantidadePassagens() : 0;

        return ResponseEntity.ok(
                Map.of(
                        "idConta", conta.getId(),
                        "nome", conta.getNomeConta(),
                        "email", conta.getEmail(),
                        "pontos", pontos,
                        "passagens", passagens
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