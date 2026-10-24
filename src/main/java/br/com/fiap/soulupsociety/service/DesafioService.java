
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.DesafioDAO;
import br.com.fiap.soulupsociety.models.Desafio;

import java.util.List;

public class DesafioService {

    private final DesafioDAO dao = new DesafioDAO();

    // Cadastrar desafio
    public void cadastrar(Desafio desafio) {

        if (desafio == null) {
            throw new IllegalArgumentException(
                    "Não foi possível cadastrar o desafio. Verifique os dados informados."
            );
        }

        if (desafio.getNome() == null ||
                desafio.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe um nome para o desafio."
            );
        }

        if (desafio.getDescricaoDesafio() == null ||
                desafio.getDescricaoDesafio().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe uma descrição para o desafio."
            );
        }

        if (desafio.getQtdPontos() == null ||
                desafio.getQtdPontos() <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade de pontos do desafio deve ser maior que zero."
            );
        }

        dao.cadastrar(desafio);
    }

    // Consultar desafio por ID
    public Desafio consultar(Integer id) {

        validarId(id);

        Desafio desafio = dao.consultarPorId(id);

        if (desafio == null) {
            throw new IllegalArgumentException(
                    "Não encontramos o desafio solicitado."
            );
        }

        return desafio;
    }

    // Listar todos os desafios
    public List<Desafio> listar() {
        return dao.listar();
    }

    // Atualizar desafio
    public void atualizar(Desafio desafio) {

        if (desafio == null) {
            throw new IllegalArgumentException(
                    "Não foi possível atualizar o desafio. Verifique os dados informados."
            );
        }

        validarId(desafio.getId());

        if (desafio.getNome() == null ||
                desafio.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe um nome para o desafio."
            );
        }

        if (desafio.getDescricaoDesafio() == null ||
                desafio.getDescricaoDesafio().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe uma descrição para o desafio."
            );
        }

        if (desafio.getQtdPontos() == null ||
                desafio.getQtdPontos() <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade de pontos do desafio deve ser maior que zero."
            );
        }

        if (dao.consultarPorId(desafio.getId()) == null) {
            throw new IllegalArgumentException(
                    "Não encontramos o desafio que você deseja atualizar."
            );
        }

        dao.atualizar(desafio);
    }

    // Excluir desafio
    public void deletar(Integer id) {

        validarId(id);

        if (dao.consultarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "Não encontramos o desafio que você deseja excluir."
            );
        }

        dao.deletar(id);
    }

    // Validar ID recebido nas operações
    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O identificador do desafio informado é inválido."
            );
        }
    }
}
