
package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Carteira;
import br.com.fiap.soulupsociety.models.Conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarteiraDAO {

    private Connection conexao;

    public CarteiraDAO() {
        conexao = ConnectionFactory.obterConexao();
    }

    // CREATE - Criar carteira
    public void criar(Carteira carteira) {

        String sql = "INSERT INTO carteira " +
                "(qtd_pontos, qtd_vales_desconto, qtd_passagens, " +
                "CONTA_id_conta) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, carteira.getQuantidadePontos());
            ps.setInt(2, carteira.getValesDesconto());
            ps.setInt(3, carteira.getQuantidadePassagens());
            ps.setInt(4, carteira.getConta().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar carteira.", e);
        }
    }

    // READ - Consultar carteira por ID
    public Carteira consultarPorId(Integer id) {

        String sql = "SELECT * FROM carteira WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Carteira carteira = new Carteira();

                    carteira.setId(rs.getInt("id_carteira"));
                    carteira.setQuantidadePontos(
                            rs.getInt("qtd_pontos")
                    );
                    carteira.setValesDesconto(
                            rs.getInt("qtd_vales_desconto")
                    );
                    carteira.setQuantidadePassagens(
                            rs.getInt("qtd_passagens")
                    );

                    Conta conta = new Conta();
                    conta.setId(rs.getInt("CONTA_id_conta"));
                    carteira.setConta(conta);

                    return carteira;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar carteira.", e);
        }

        return null;
    }

    // READ - Consultar carteira por conta
    public Carteira consultarPorConta(Integer idConta) {

        String sql = "SELECT * FROM carteira WHERE CONTA_id_conta = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, idConta);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Carteira carteira = new Carteira();

                    carteira.setId(rs.getInt("id_carteira"));
                    carteira.setQuantidadePontos(
                            rs.getInt("qtd_pontos")
                    );
                    carteira.setValesDesconto(
                            rs.getInt("qtd_vales_desconto")
                    );
                    carteira.setQuantidadePassagens(
                            rs.getInt("qtd_passagens")
                    );

                    Conta conta = new Conta();
                    conta.setId(rs.getInt("CONTA_id_conta"));
                    carteira.setConta(conta);

                    return carteira;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao consultar carteira por conta.", e
            );
        }

        return null;
    }

    // UPDATE - Adicionar pontos
    public void adicionarPontos(Integer id, int quantidade) {

        String sql = "UPDATE carteira " +
                "SET qtd_pontos = qtd_pontos + ? " +
                "WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar pontos.", e);
        }
    }

    // UPDATE - Remover pontos
    public void removerPontos(Integer id, int quantidade) {

        String sql = "UPDATE carteira " +
                "SET qtd_pontos = qtd_pontos - ? " +
                "WHERE id_carteira = ? " +
                "AND qtd_pontos >= ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, id);
            ps.setInt(3, quantidade);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover pontos.", e);
        }
    }

    // UPDATE - Adicionar vale-desconto
    public void adicionarValesDesconto(Integer id, int quantidade) {

        String sql = "UPDATE carteira " +
                "SET qtd_vales_desconto = qtd_vales_desconto + ? " +
                "WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao adicionar vales-desconto.", e
            );
        }
    }

    // UPDATE - Remover vale-desconto
    public void removerValesDesconto(Integer id, int quantidade) {

        String sql = "UPDATE carteira " +
                "SET qtd_vales_desconto = qtd_vales_desconto - ? " +
                "WHERE id_carteira = ? " +
                "AND qtd_vales_desconto >= ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, id);
            ps.setInt(3, quantidade);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao remover vales-desconto.", e
            );
        }
    }

    // UPDATE - Adicionar passagem
    public void adicionarPassagem(Integer id, int quantidade) {

        String sql = "UPDATE carteira " +
                "SET qtd_passagens = qtd_passagens + ? " +
                "WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar passagens.", e);
        }
    }

    // DELETE - Deletar carteira
    public void deletar(Integer id) {

        String sql = "DELETE FROM carteira WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar carteira.", e);
        }
    }
}
