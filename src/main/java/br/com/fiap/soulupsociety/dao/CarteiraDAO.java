package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Carteira;
import br.com.fiap.soulupsociety.models.Conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarteiraDAO {

    private Connection conexao;


    // Create / Insert - CRUD
    public void criarCarteira(Carteira carteira) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "INSERT INTO CARTEIRA " +
                "(ID_CARTEIRA, QTD_PONTOS, QTD_VALESDESCONTO, QTD_PASSAGENS, CONTA_IDCONTA) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, carteira.getId());
            ps.setInt(2, carteira.getQuantidadePontos());
            ps.setInt(3, carteira.getValesDesconto());
            ps.setInt(4, carteira.getQuantidadePassagens());
            ps.setInt(5, carteira.getConta().getId());

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Read / Select - CRUD
    public Carteira consultarPorId(Integer id) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "SELECT * FROM CARTEIRA WHERE ID_CARTEIRA = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Carteira carteira = new Carteira();

                carteira.setId(rs.getInt("ID_CARTEIRA"));
                carteira.setQuantidadePontos(rs.getInt("QTD_PONTOS"));
                carteira.setValesDesconto(rs.getInt("QTD_VALESDESCONTO"));
                carteira.setQuantidadePassagens(rs.getInt("QTD_PASSAGENS"));

                Conta conta = new Conta();

                conta.setId(rs.getInt("CONTA_IDCONTA"));

                carteira.setConta(conta);

                rs.close();
                ps.close();
                conexao.close();

                return carteira;
            }

            rs.close();
            ps.close();
            conexao.close();

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Busca a carteira de uma conta específica
    public Carteira consultarPorConta(Integer idConta) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "SELECT * FROM CARTEIRA WHERE CONTA_IDCONTA = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, idConta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Carteira carteira = new Carteira();

                carteira.setId(rs.getInt("ID_CARTEIRA"));
                carteira.setQuantidadePontos(rs.getInt("QTD_PONTOS"));
                carteira.setValesDesconto(rs.getInt("QTD_VALESDESCONTO"));
                carteira.setQuantidadePassagens(rs.getInt("QTD_PASSAGENS"));

                Conta conta = new Conta();

                conta.setId(rs.getInt("CONTA_IDCONTA"));

                carteira.setConta(conta);

                rs.close();
                ps.close();
                conexao.close();

                return carteira;
            }

            rs.close();
            ps.close();
            conexao.close();

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Update - Adiciona pontos
    public void adicionarPontos(Integer idCarteira, Integer quantidade) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "UPDATE CARTEIRA " +
                "SET QTD_PONTOS = QTD_PONTOS + ? " +
                "WHERE ID_CARTEIRA = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, quantidade);
            ps.setInt(2, idCarteira);

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Update - Remove pontos
    public void removerPontos(Integer idCarteira, Integer quantidade) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "UPDATE CARTEIRA " +
                "SET QTD_PONTOS = QTD_PONTOS - ? " +
                "WHERE ID_CARTEIRA = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, quantidade);
            ps.setInt(2, idCarteira);

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Update - Adiciona passagem
    public void adicionarPassagem(Integer idCarteira) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "UPDATE CARTEIRA " +
                "SET QTD_PASSAGENS = QTD_PASSAGENS + 1 " +
                "WHERE ID_CARTEIRA = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, idCarteira);

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Delete - CRUD
    public void deletarCarteira(Integer id) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "DELETE FROM CARTEIRA WHERE ID_CARTEIRA = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}