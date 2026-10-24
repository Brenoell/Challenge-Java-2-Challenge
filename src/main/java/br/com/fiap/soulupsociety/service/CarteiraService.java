
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.CarteiraDAO;
import br.com.fiap.soulupsociety.models.Carteira;

import java.util.List;

public class CarteiraService {

    private CarteiraDAO dao = new CarteiraDAO();

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

    // READ - Consultar carteira por ID
    public Carteira consultar(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID da carteira deve ser positivo."
            );
        }

        return dao.consultarPorId(id);
    }

    // READ - Listar carteiras
    public List<Carteira> listar() {
        return dao.listar();
    }

    // UPDATE - Adicionar pontos
    public void adicionarPontos(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);

        dao.adicionarPontos(id, quantidade);
    }

    // UPDATE - Remover pontos
    public void removerPontos(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);

        dao.removerPontos(id, quantidade);
    }

    // UPDATE - Adicionar vales-desconto
    public void adicionarValesDesconto(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);

        dao.adicionarValesDesconto(id, quantidade);
    }

    // UPDATE - Remover vales-desconto
    public void removerValesDesconto(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);

        dao.removerValesDesconto(id, quantidade);
    }

    // UPDATE - Adicionar passagens
    public void adicionarPassagem(Integer id, int quantidade) {

        validarIdEQuantidade(id, quantidade);

        dao.adicionarPassagem(id, quantidade);
    }

    // DELETE - Deletar carteira
    public void deletar(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID da carteira deve ser positivo."
            );
        }

        dao.deletar(id);
    }

    // Validação reutilizada pelos métodos de movimentação
    private void validarIdEQuantidade(Integer id, int quantidade) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID da carteira deve ser positivo."
            );
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero."
            );
        }
    }
}
