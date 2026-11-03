
package br.com.fiap.soulupsociety.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Login inválido: HTTP 401
    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<?> tratarCredenciaisInvalidas(
            CredenciaisInvalidasException erro) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", erro.getMessage()));
    }

    // Registro não encontrado: HTTP 404
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<?> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException erro) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("erro", erro.getMessage()));
    }

    // Dados inválidos: HTTP 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> tratarDadosInvalidos(
            IllegalArgumentException erro) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("erro", erro.getMessage()));
    }

    // Qualquer outro erro inesperado: HTTP 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> tratarErroInterno(Exception erro) {

        // Mostra o erro completo no console do IntelliJ
        System.out.println("ERRO INTERNO NO SERVIDOR:");
        erro.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("erro", "Ocorreu um erro interno no servidor"));
    }
}
