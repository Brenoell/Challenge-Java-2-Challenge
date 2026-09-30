package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Desafio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DesafioDAO {

    private Connection conexao;


    // Cadastro de desafio pelo moderador
    public void cadastrar(Desafio desafio) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "INSERT INTO DESAFIO " +
                "(ID_DESAFIO, NM_DESAFIO, DS_DESAFIO, QTD_PONTOSDADOS) " +
                "VALUES (?, ?, ?, ?)";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, desafio.getId());
            ps.setString(2, desafio.getNome());
            ps.setString(3, desafio.getDescricaoDesafio());
            ps.setInt(4, desafio.getQtdPontos());

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Lista todos os desafios
    public List<Desafio> listarDesafios() {

        conexao = ConnectionFactory.obterConexao();

        List<Desafio> desafios = new ArrayList<>();

        String sql = "SELECT * FROM DESAFIO";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Desafio desafio = new Desafio();

                desafio.setId(rs.getInt("ID_DESAFIO"));
                desafio.setNome(rs.getString("NM_DESAFIO"));
                desafio.setDescricaoDesafio(
                        rs.getString("DS_DESAFIO")
                );
                desafio.setQtdPontos(
                        rs.getInt("QTD_PONTOSDADOS")
                );

                desafios.add(desafio);
            }

            rs.close();
            ps.close();
            conexao.close();

            return desafios;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Consulta um desafio pelo ID
    public Desafio consultarPorId(Integer id) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "SELECT * FROM DESAFIO WHERE ID_DESAFIO = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Desafio desafio = new Desafio();

                desafio.setId(
                        rs.getInt("ID_DESAFIO")
                );

                desafio.setNome(
                        rs.getString("NM_DESAFIO")
                );

                desafio.setDescricaoDesafio(
                        rs.getString("DS_DESAFIO")
                );

                desafio.setQtdPontos(
                        rs.getInt("QTD_PONTOSDADOS")
                );

                rs.close();
                ps.close();
                conexao.close();

                return desafio;
            }

            rs.close();
            ps.close();
            conexao.close();

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Atualização de desafio pelo moderador
    public void atualizar(Desafio desafio) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "UPDATE DESAFIO SET " +
                "NM_DESAFIO = ?, " +
                "DS_DESAFIO = ?, " +
                "QTD_PONTOSDADOS = ? " +
                "WHERE ID_DESAFIO = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, desafio.getNome());
            ps.setString(2, desafio.getDescricaoDesafio());
            ps.setInt(3, desafio.getQtdPontos());
            ps.setInt(4, desafio.getId());

            ps.executeUpdate();

            ps.close();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Exclusão de desafio pelo moderador
    public void deletar(Integer id) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "DELETE FROM DESAFIO WHERE ID_DESAFIO = ?";

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