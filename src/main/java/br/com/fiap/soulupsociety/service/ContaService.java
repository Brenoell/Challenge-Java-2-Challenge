
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.ContaDAO;
import br.com.fiap.soulupsociety.exception.CredenciaisInvalidasException;
import br.com.fiap.soulupsociety.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulupsociety.models.Conta;


import java.util.List;

public class ContaService {

    private final ContaDAO dao = new ContaDAO();

    // CREATE - Cadastrar conta
    public void cadastrar(Conta conta) {

        if (conta == null) {
            throw new IllegalArgumentException(
                    "A conta não pode ser nula."
            );
        }

        validarDados(conta);

        dao.cadastrar(conta);
    }

    // LOGIN
    public Conta login(String email, String senha) {

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O e-mail é obrigatório."
            );
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A senha é obrigatória."
            );
        }

        Conta conta = dao.login(email, senha);

        if (conta == null) {
            throw new CredenciaisInvalidasException(
                    "E-mail ou senha incorretos."
            );
        }

        return conta;
    }

    // READ - Consultar conta por ID
    public Conta consultar(Integer id) {

        validarId(id);

        Conta conta = dao.consultarPorId(id);

        if (conta == null) {
            throw new RecursoNaoEncontradoException(
                    "Conta não encontrada."
            );
        }

        return conta;
    }

    // READ - Listar todas as contas
    public List<Conta> listar() {
        return dao.listar();
    }

    // UPDATE - Atualizar conta
    public void atualizar(Conta conta) {

        if (conta == null) {
            throw new IllegalArgumentException(
                    "A conta não pode ser nula."
            );
        }

        validarId(conta.getId());
        validarDados(conta);

        // Verifica se a conta existe
        consultar(conta.getId());

        boolean atualizado = dao.atualizar(conta);

        if (!atualizado) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível atualizar a conta."
            );
        }
    }

    // DELETE - Deletar conta
    public void deletar(Integer id) {

        validarId(id);

        // Verifica se a conta existe
        consultar(id);

        boolean deletado = dao.deletar(id);

        if (!deletado) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível excluir a conta."
            );
        }
    }

    // VALIDAÇÕES
    private void validarDados(Conta conta) {

        if (conta.getNomeConta() == null ||
                conta.getNomeConta().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "O nome da conta é obrigatório."
            );
        }

        if (conta.getEmail() == null ||
                conta.getEmail().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "O e-mail é obrigatório."
            );
        }

        if (!conta.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            throw new IllegalArgumentException(
                    "O e-mail informado é inválido."
            );
        }

        if (conta.getSenha() == null ||
                conta.getSenha().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "A senha é obrigatória."
            );
        }

        if (conta.getSenha().length() < 6) {
            throw new IllegalArgumentException(
                    "A senha deve ter pelo menos 6 caracteres."
            );
        }

        if (conta.getUsuario() == null ||
                conta.getUsuario().getId() == null ||
                conta.getUsuario().getId() <= 0) {

            throw new IllegalArgumentException(
                    "É necessário informar um usuário válido."
            );
        }
    }

    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID deve ser um número positivo."
            );
        }
    }
}
