package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Usuario;

import java.sql.*;
import java.util.List;

public class UsuarioDAO {

    // Create / Insert - CRUD
    public void cadastrar(Usuario usuario) {
        Connection conexao = ConnectionFactory.obterConexao();
        String sql = """
                INSERT INTO USUARIO
                    (ID_USUARIO, NM_USUARIO, DT_NASCIMENTO, NUM_CPF)
                VALUES
                    (SEQ_USUARIO.NEXTVAL, ?, ?, ?)
                
                """;
        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, usuario.getNome());
            ps.setDate(2, Date.valueOf(usuario.getDataNascimento()));
            ps.setLong(3, usuario.getNumeroCpf());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Read / Select - CRUD
    public Usuario consultarPorId(Integer id) {

        Connection conexao = ConnectionFactory.obterConexao();

        String sql = """
        SELECT *
        FROM USUARIO
        WHERE ID_USUARIO = ?
        """;

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("ID_USUARIO"));
                usuario.setNome(rs.getString("NM_USUARIO"));
                usuario.setDataNascimento(
                        rs.getDate("DT_NASCIMENTO").toLocalDate()
                );
                usuario.setNumeroCpf(rs.getLong("NUM_CPF"));

                return usuario;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    // Consulta específica
    public Usuario consultarPorCpf(Long numeroCpf) {
        Connection conexao = ConnectionFactory.obterConexao();

        String sql = """
            SELECT *
            FROM USUARIO
            WHERE NUM_CPF = ?
            """;
        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setLong(1, numeroCpf);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("ID_USUARIO"));
                usuario.setNome(rs.getString("NM_USUARIO"));
                usuario.setDataNascimento(
                        rs.getDate("DT_NASCIMENTO").toLocalDate()
                );
                usuario.setNumeroCpf(rs.getLong("NUM_CPF"));

                return usuario;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Update - CRUD
    public void atualizarUsuario(Usuario usuario) {

        Connection conexao = ConnectionFactory.obterConexao();

        String sql = """
            UPDATE USUARIO
            SET NM_USUARIO = ?,
                DT_NASCIMENTO = ?,
                NUM_CPF = ?
            WHERE ID_USUARIO = ?
            """;

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, usuario.getNome());
            ps.setDate(2, Date.valueOf(usuario.getDataNascimento()));
            ps.setLong(3, usuario.getNumeroCpf());
            ps.setInt(4, usuario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Delete - CRUD
    public void deletarUsuario(Integer id) {

        Connection conexao = ConnectionFactory.obterConexao();

        String sql = """
            DELETE FROM USUARIO
            WHERE ID_USUARIO = ?
            """;

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}