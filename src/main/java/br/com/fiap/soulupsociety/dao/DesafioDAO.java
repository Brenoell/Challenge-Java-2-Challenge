
package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Desafio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DesafioDAO {

    // CREATE - Cadastrar desafio
    public void cadastrar(Desafio desafio) {

        String sql = "INSERT INTO desafio " +
                "(nm_desafio, ds_desafio, qtd_pontos_dados) " +
                "VALUES (?, ?, ?)";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, desafio.getNome());
            ps.setString(2, desafio.getDescricaoDesafio());
            ps.setInt(3, desafio.getQtdPontos());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar desafio.", e);
        }
    }

    // READ - Consultar desafio por ID
    public Desafio consultarPorId(Integer id) {

        String sql = "SELECT * FROM desafio WHERE id_desafio = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return montarDesafio(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar desafio.", e);
        }

        return null;
    }

    // READ - Listar desafios
    public List<Desafio> listar() {

        List<Desafio> desafios = new ArrayList<>();

        String sql = "SELECT * FROM desafio";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                desafios.add(montarDesafio(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar desafios.", e);
        }

        return desafios;
    }

    // UPDATE - Atualizar desafio
    public boolean atualizar(Desafio desafio) {

        String sql = "UPDATE desafio SET " +
                "nm_desafio = ?, ds_desafio = ?, qtd_pontos_dados = ? " +
                "WHERE id_desafio = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, desafio.getNome());
            ps.setString(2, desafio.getDescricaoDesafio());
            ps.setInt(3, desafio.getQtdPontos());
            ps.setInt(4, desafio.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar desafio.", e);
        }
    }

    // DELETE - Deletar desafio
    public boolean deletar(Integer id) {

        String sql = "DELETE FROM desafio WHERE id_desafio = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar desafio.", e);
        }
    }

    // Método auxiliar para montar o objeto Desafio
    private Desafio montarDesafio(ResultSet rs) throws SQLException {

        Desafio desafio = new Desafio();

        desafio.setId(rs.getInt("id_desafio"));
        desafio.setNome(rs.getString("nm_desafio"));
        desafio.setDescricaoDesafio(rs.getString("ds_desafio"));
        desafio.setQtdPontos(rs.getInt("qtd_pontos_dados"));

        return desafio;
    }
}
