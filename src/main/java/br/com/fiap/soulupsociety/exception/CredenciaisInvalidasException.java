package br.com.fiap.soulupsociety.exception;

/** Lançada quando e-mail ou senha estão incorretos. Vira HTTP 401. */
public class CredenciaisInvalidasException extends RuntimeException {

    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }
}
 