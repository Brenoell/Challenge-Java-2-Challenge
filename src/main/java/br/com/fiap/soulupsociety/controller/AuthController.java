
package br.com.fiap.soulupsociety.controller;

import br.com.fiap.soulupsociety.dao.CarteiraDAO;
import br.com.fiap.soulupsociety.dto.LoginDTO;
import br.com.fiap.soulupsociety.models.Carteira;
import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.service.ContaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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

    private final ContaService contaService = new ContaService();
    private final CarteiraDAO carteiraDAO = new CarteiraDAO();

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDTO dados,
            HttpServletRequest request) {

        if (dados == null) {
            throw new IllegalArgumentException(
                    "Informe o e-mail e a senha."
            );
        }

        // Verifica e-mail e senha
        Conta conta = contaService.login(
                dados.email(),
                dados.senha()
        );

        // Encerra uma sessão anterior, se existir
        HttpSession sessaoAntiga = request.getSession(false);

        if (sessaoAntiga != null) {
            sessaoAntiga.invalidate();
        }

        // Cria uma nova sessão
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

    // VERIFICAR SESSÃO
    @GetMapping("/sessao")
    public ResponseEntity<?> verificarSessao(
            HttpServletRequest request) {

        HttpSession sessao = request.getSession(false);

        if (sessao == null ||
                sessao.getAttribute("idConta") == null) {

            return ResponseEntity
                    .status(401)
                    .body(Map.of("autenticado", false));
        }

        return ResponseEntity.ok(
                Map.of(
                        "autenticado", true,
                        "idConta", sessao.getAttribute("idConta")
                )
        );
    }

    // DADOS DA CONTA LOGADA
    @GetMapping("/me")
    public ResponseEntity<?> me(
            HttpServletRequest request) {

        HttpSession sessao = request.getSession(false);

        if (sessao == null ||
                sessao.getAttribute("idConta") == null) {

            return ResponseEntity
                    .status(401)
                    .body(Map.of("autenticado", false));
        }

        Integer idConta = (Integer) sessao.getAttribute("idConta");

        Conta conta = contaService.consultar(idConta);

        Carteira carteira = carteiraDAO.consultarPorConta(idConta);

        int pontos = 0;
        int passagens = 0;

        if (carteira != null) {
            pontos = carteira.getQuantidadePontos();
            passagens = carteira.getQuantidadePassagens();
        }

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

    // LOGOUT
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
