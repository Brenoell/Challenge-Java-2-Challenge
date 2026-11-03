
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.CarteiraDAO;
import br.com.fiap.soulupsociety.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulupsociety.models.Carteira;

import java.util.List;

public class CarteiraService {

    private final CarteiraDAO dao = new CarteiraDAO();

    private static final int CUSTO_PASSAGEM = 1000;

    // CREATE - Cadastrar carteira
    public void cadastrar(Carteira carteira) {

        if (carteira == null) {
            throw new IllegalArgumentException(
                    "A carteira não pode ser nula."
            );
        }

        if (carteira.getConta() == null ||
                carteira.getConta().getId() == null ||
                carteira.getConta().getId() <= 0) {

            throw new IllegalArgumentException(
                    "A carteira precisa estar vinculada a uma conta válida."
            );
        }

        if (carteira.getQuantidadePontos() < 0 ||
                carteira.getValesDesconto() < 0 ||
                carteira.getQuantidadePassagens() < 0) {

            throw new IllegalArgumentException(
                    "Os pontos, vales-desconto e passagens não podem ser negativos."
            );
        }

        dao.criar(carteira);
    }

    // READ - Listar todas as carteiras
    public List<Carteira> listar() {
        return dao.listar();
    }

    // READ - Consultar carteira por ID
    public Carteira consultar(Integer id) {

        validarId(id);

        Carteira carteira = dao.consultarPorId(id);

        if (carteira == null) {
            throw new RecursoNaoEncontradoException(
                    "Carteira não encontrada."
            );
        }

        return carteira;
    }

    // READ - Consultar carteira pela conta
    public Carteira consultarPorConta(Integer idConta) {

        if (idConta == null || idConta <= 0) {
            throw new IllegalArgumentException(
                    "O ID da conta deve ser positivo."
            );
        }

        Carteira carteira = dao.consultarPorConta(idConta);

        if (carteira == null) {
            throw new RecursoNaoEncontradoException(
                    "Carteira dessa conta não encontrada."
            );
        }

        return carteira;
    }

    // UPDATE - Adicionar pontos
    public void adicionarPontos(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);
        consultar(id);

        if (!dao.adicionarPontos(id, quantidade)) {
            throw new RecursoNaoEncontradoException(
                    "Carteira não encontrada."
            );
        }
    }

    // UPDATE - Remover pontos
    public void removerPontos(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);

        Carteira carteira = consultar(id);

        if (carteira.getQuantidadePontos() < quantidade) {
            throw new IllegalArgumentException(
                    "Você não possui pontos suficientes."
            );
        }

        if (!dao.removerPontos(id, quantidade)) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível atualizar a carteira."
            );
        }
    }

    // UPDATE - Adicionar vales-desconto
    public void adicionarValesDesconto(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);
        consultar(id);

        if (!dao.adicionarValesDesconto(id, quantidade)) {
            throw new RecursoNaoEncontradoException(
                    "Carteira não encontrada."
            );
        }
    }

    // UPDATE - Remover vales-desconto
    public void removerValesDesconto(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);

        Carteira carteira = consultar(id);

        if (carteira.getValesDesconto() < quantidade) {
            throw new IllegalArgumentException(
                    "Você não possui vales-desconto suficientes."
            );
        }

        if (!dao.removerValesDesconto(id, quantidade)) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível atualizar a carteira."
            );
        }
    }

    // UPDATE - Adicionar passagens
    public void adicionarPassagem(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);
        consultar(id);

        if (!dao.adicionarPassagem(id, quantidade)) {
            throw new RecursoNaoEncontradoException(
                    "Carteira não encontrada."
            );
        }
    }

    // UPDATE - Trocar pontos por passagem
    public boolean trocarPontosPorPassagem(Integer id) {

        Carteira carteira = consultar(id);

        if (carteira.getQuantidadePontos() < CUSTO_PASSAGEM) {
            throw new IllegalArgumentException(
                    "Você não possui pontos suficientes para trocar por uma passagem."
            );
        }

        if (!dao.trocarPontosPorPassagem(id, CUSTO_PASSAGEM)) {
            throw new IllegalArgumentException(
                    "Não foi possível realizar a troca. Verifique seu saldo."
            );
        }

        return true;
    }

    // DELETE - Deletar carteira
    public void deletar(Integer id) {

        validarId(id);

        if (!dao.deletar(id)) {
            throw new RecursoNaoEncontradoException(
                    "Carteira não encontrada."
            );
        }
    }

    // Validação de ID
    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID da carteira deve ser positivo."
            );
        }
    }

    // Validação de ID e quantidade
    private void validarIdEQuantidade(Integer id, int quantidade) {

        validarId(id);

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero."
            );
        }
    }
}
