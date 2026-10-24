
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.ContaDAO;
import br.com.fiap.soulupsociety.models.Conta;

import java.time.LocalDate;
import java.util.List;

public class ContaService {

    private ContaDAO dao = new ContaDAO();

    // CREATE - Cadastrar conta
    public void cadastrar(Conta conta) {

        if (conta == null) {
            throw new IllegalArgumentException(
                    "A conta não pode ser nula."
            );
        }

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

        if (conta.getDataCriacao() == null) {
            throw new IllegalArgumentException(
                    "A data de criação é obrigatória."
            );
        }

        if (conta.getDataCriacao().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data de criação não pode ser futura."
            );
        }

        if (conta.getUsuario() == null ||
                conta.getUsuario().getId() == null ||
                conta.getUsuario().getId() <= 0) {
            throw new IllegalArgumentException(
                    "É necessário informar um usuário válido."
            );
        }

        if (conta.getTipoConta() == null ||
                conta.getTipoConta().getId() == null ||
                conta.getTipoConta().getId() <= 0) {
            throw new IllegalArgumentException(
                    "É necessário informar um tipo de conta válido."
            );
        }

        dao.cadastrar(conta);
    }

    // READ - Consultar conta por ID
    public Conta consultar(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID deve ser um número positivo."
            );
        }

        return dao.consultarPorId(id);
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

        if (conta.getId() == null || conta.getId() <= 0) {
            throw new IllegalArgumentException(
                    "É necessário informar um ID válido para atualizar."
            );
        }

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

        if (conta.getDataCriacao() == null) {
            throw new IllegalArgumentException(
                    "A data de criação é obrigatória."
            );
        }

        if (conta.getDataCriacao().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data de criação não pode ser futura."
            );
        }

        if (conta.getUsuario() == null ||
                conta.getUsuario().getId() == null ||
                conta.getUsuario().getId() <= 0) {
            throw new IllegalArgumentException(
                    "É necessário informar um usuário válido."
            );
        }

        if (conta.getTipoConta() == null ||
                conta.getTipoConta().getId() == null ||
                conta.getTipoConta().getId() <= 0) {
            throw new IllegalArgumentException(
                    "É necessário informar um tipo de conta válido."
            );
        }

        dao.atualizar(conta);
    }

    // DELETE - Deletar conta
    public void deletar(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID deve ser um número positivo."
            );
        }

        dao.deletar(id);
    }
}
