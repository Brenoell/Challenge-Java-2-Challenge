package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.enums.TipoContaEnum;
import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.models.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaDAO {

    private Connection conexao;


    // Create / Insert - CRUD
    public void cadastrar(Conta conta) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "INSERT INTO CONTA " +
                "(ID_CONTA, NM_CONTA, DS_EMAIL, DT_DESCRICAO, DS_BIO, USUARIO_IDUSUARIO, TP_CONTA) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, conta.getId());
            ps.setString(2, conta.getNomeConta());
            ps.setString(3, conta.getEmail());
            ps.setDate(4, Date.valueOf(conta.getDataCriacao()));
            ps.setString(5, conta.getBio());
            ps.setInt(6, conta.getUsuario().getId());

            // Toda conta cadastrada será USUARIO
            ps.setString(7, TipoContaEnum.USUARIO.name());

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Read / Select - CRUD
    public Conta consultarPorId(Integer id) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "SELECT * FROM CONTA WHERE ID_CONTA = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Conta conta = new Conta();

                conta.setId(rs.getInt("ID_CONTA"));
                conta.setNomeConta(rs.getString("NM_CONTA"));
                conta.setEmail(rs.getString("DS_EMAIL"));
                conta.setBio(rs.getString("DS_BIO"));

                Date data = rs.getDate("DT_DESCRICAO");

                if (data != null) {
                    conta.setDataCriacao(data.toLocalDate());
                }

                conta.setTipoConta(
                        TipoContaEnum.valueOf(rs.getString("TP_CONTA"))
                );

                Usuario usuario = new Usuario();

                usuario.setId(
                        rs.getInt("USUARIO_IDUSUARIO")
                );

                conta.setUsuario(usuario);

                rs.close();
                ps.close();
                conexao.close();

                return conta;
            }

            rs.close();
            ps.close();
            conexao.close();

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Busca uma conta pelo e-mail
    public Conta consultarPorEmail(String email) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "SELECT * FROM CONTA WHERE DS_EMAIL = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Conta conta = new Conta();

                conta.setId(rs.getInt("ID_CONTA"));
                conta.setNomeConta(rs.getString("NM_CONTA"));
                conta.setEmail(rs.getString("DS_EMAIL"));
                conta.setBio(rs.getString("DS_BIO"));

                Date data = rs.getDate("DT_DESCRICAO");

                if (data != null) {
                    conta.setDataCriacao(data.toLocalDate());
                }

                conta.setTipoConta(
                        TipoContaEnum.valueOf(rs.getString("TP_CONTA"))
                );

                Usuario usuario = new Usuario();

                usuario.setId(
                        rs.getInt("USUARIO_IDUSUARIO")
                );

                conta.setUsuario(usuario);

                rs.close();
                ps.close();
                conexao.close();

                return conta;
            }

            rs.close();
            ps.close();
            conexao.close();

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Update - CRUD
    public void atualizarConta(Conta conta) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "UPDATE CONTA SET " +
                "NM_CONTA = ?, " +
                "DS_EMAIL = ?, " +
                "DT_DESCRICAO = ?, " +
                "DS_BIO = ? " +
                "WHERE ID_CONTA = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, conta.getNomeConta());
            ps.setString(2, conta.getEmail());
            ps.setDate(3, Date.valueOf(conta.getDataCriacao()));
            ps.setString(4, conta.getBio());
            ps.setInt(5, conta.getId());

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Delete - CRUD
    public void deletarConta(Integer id) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "DELETE FROM CONTA WHERE ID_CONTA = ?";

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