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
    private PreparedStatement ps = null;

    public DesafioDAO() {
        conexao = ConnectionFactory.obterConexao();
    }

    public void cadastrar(Desafio desafio) {

        String sql = "INSERT INTO desafio " +
                "(nm_desafio, ds_desafio, qtd_pontos_dados) " +
                "VALUES (?, ?, ?)";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, desafio.getNome());
            ps.setString(2, desafio.getDescricaoDesafio());
            ps.setInt(3, desafio.getQtdPontos());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Desafio consultarPorId(Integer id) {

        String sql = "SELECT * FROM desafio WHERE id_desafio = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Desafio desafio = new Desafio();

                desafio.setId(rs.getInt("id_desafio"));
                desafio.setNome(rs.getString("nm_desafio"));
                desafio.setDescricaoDesafio(rs.getString("ds_desafio"));
                desafio.setQtdPontos(rs.getInt("qtd_pontos_dados"));

                return desafio;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Desafio> listar() {

        List<Desafio> desafios = new ArrayList<>();

        String sql = "SELECT * FROM desafio";

        try {
            ps = conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Desafio desafio = new Desafio();

                desafio.setId(rs.getInt("id_desafio"));
                desafio.setNome(rs.getString("nm_desafio"));
                desafio.setDescricaoDesafio(rs.getString("ds_desafio"));
                desafio.setQtdPontos(rs.getInt("qtd_pontos_dados"));

                desafios.add(desafio);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return desafios;
    }

    public void atualizar(Desafio desafio) {

        String sql = "UPDATE desafio SET " +
                "nm_desafio = ?, ds_desafio = ?, qtd_pontos_dados = ? " +
                "WHERE id_desafio = ?";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, desafio.getNome());
            ps.setString(2, desafio.getDescricaoDesafio());
            ps.setInt(3, desafio.getQtdPontos());
            ps.setInt(4, desafio.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer id) {

        String sql = "DELETE FROM desafio WHERE id_desafio = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}