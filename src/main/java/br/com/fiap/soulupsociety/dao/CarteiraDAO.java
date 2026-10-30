package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Carteira;
import br.com.fiap.soulupsociety.models.Conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarteiraDAO {

    private Connection conexao;

    public CarteiraDAO() {
        conexao = ConnectionFactory.obterConexao();
    }

    public void criar(Carteira carteira) {

        String sql = "INSERT INTO carteira " +
                "(qtd_pontos, qtd_vales_desconto, qtd_passagens, CONTA_id_conta) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, carteira.getQuantidadePontos());
            ps.setInt(2, carteira.getValesDesconto());
            ps.setInt(3, carteira.getQuantidadePassagens());
            ps.setInt(4, carteira.getConta().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Carteira consultarPorId(Integer id) {

        String sql = "SELECT * FROM carteira WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Carteira carteira = new Carteira();

                carteira.setId(rs.getInt("id_carteira"));
                carteira.setQuantidadePontos(rs.getInt("qtd_pontos"));
                carteira.setValesDesconto(rs.getInt("qtd_vales_desconto"));
                carteira.setQuantidadePassagens(rs.getInt("qtd_passagens"));

                Conta conta = new Conta();
                conta.setId(rs.getInt("CONTA_id_conta"));

                carteira.setConta(conta);

                return carteira;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Carteira consultarPorConta(Integer idConta) {

        String sql = "SELECT * FROM carteira WHERE CONTA_id_conta = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, idConta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Carteira carteira = new Carteira();

                carteira.setId(rs.getInt("id_carteira"));
                carteira.setQuantidadePontos(rs.getInt("qtd_pontos"));
                carteira.setValesDesconto(rs.getInt("qtd_vales_desconto"));
                carteira.setQuantidadePassagens(rs.getInt("qtd_passagens"));

                Conta conta = new Conta();
                conta.setId(rs.getInt("CONTA_id_conta"));

                carteira.setConta(conta);

                return carteira;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Carteira> listar() {

        String sql = "SELECT * FROM carteira";

        List<Carteira> carteiras = new ArrayList<>();

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Carteira carteira = new Carteira();

                carteira.setId(rs.getInt("id_carteira"));
                carteira.setQuantidadePontos(rs.getInt("qtd_pontos"));
                carteira.setValesDesconto(rs.getInt("qtd_vales_desconto"));
                carteira.setQuantidadePassagens(rs.getInt("qtd_passagens"));

                Conta conta = new Conta();
                conta.setId(rs.getInt("CONTA_id_conta"));

                carteira.setConta(conta);

                carteiras.add(carteira);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carteiras;
    }

    public void adicionarPontos(Integer id, int quantidade) {

        String sql = "UPDATE carteira " +
                "SET qtd_pontos = qtd_pontos + ? " +
                "WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void adicionarPontosPorConta(Integer idConta, int quantidade) {

        String sql = "UPDATE carteira " +
                "SET qtd_pontos = qtd_pontos + ? " +
                "WHERE CONTA_id_conta = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, idConta);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
            throw new RuntimeException(e);
        }
    }

    public void adicionarValesDesconto(Integer id, int quantidade) {

        String sql = "UPDATE carteira " +
                "SET qtd_vales_desconto = qtd_vales_desconto + ? " +
                "WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
            throw new RuntimeException(e);
        }
    }

    public void adicionarPassagem(Integer id, int quantidade) {

        String sql = "UPDATE carteira " +
                "SET qtd_passagens = qtd_passagens + ? " +
                "WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer id) {

        String sql = "DELETE FROM carteira WHERE id_carteira = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}