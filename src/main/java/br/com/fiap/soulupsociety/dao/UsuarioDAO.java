
package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // CREATE - Cadastrar usuário
    public Usuario cadastrar(Usuario usuario) {

        String sql = "INSERT INTO usuario " +
                "(nm_usuario, dt_nascimento, num_cpf) " +
                "VALUES (?, ?, ?)";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(
                     sql, new String[]{"ID_USUARIO"})) {

            ps.setString(1, usuario.getNome());

            ps.setDate(2, Date.valueOf(
                    usuario.getDataNascimento()
            ));

            ps.setLong(3, usuario.getNumeroCpf());

            ps.executeUpdate();

            // Recupera o ID gerado automaticamente pelo Oracle
            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }

            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário.", e);
        }
    }

    // READ - Consultar usuário por ID
    public Usuario consultarPorId(Integer id) {

        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return montarUsuario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar usuário.", e);
        }

        return null;
    }

    // READ - Consultar usuário por CPF
    public Usuario consultarPorCpf(Long cpf) {

        String sql = "SELECT * FROM usuario WHERE num_cpf = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setLong(1, cpf);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return montarUsuario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao consultar usuário pelo CPF.", e
            );
        }

        return null;
    }

    // READ - Listar todos os usuários
    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(montarUsuario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários.", e);
        }

        return usuarios;
    }

    // UPDATE - Atualizar usuário
    public boolean atualizar(Usuario usuario) {

        String sql = "UPDATE usuario SET " +
                "nm_usuario = ?, dt_nascimento = ?, num_cpf = ? " +
                "WHERE id_usuario = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());

            ps.setDate(2, Date.valueOf(
                    usuario.getDataNascimento()
            ));

            ps.setLong(3, usuario.getNumeroCpf());
            ps.setInt(4, usuario.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário.", e);
        }
    }

    // DELETE - Deletar usuário
    public boolean deletar(Integer id) {

        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário.", e);
        }
    }

    // Método auxiliar para montar um usuário
    private Usuario montarUsuario(ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setId(rs.getInt("ID_USUARIO"));
        usuario.setNome(rs.getString("NM_USUARIO"));

        usuario.setDataNascimento(
                rs.getDate("DT_NASCIMENTO").toLocalDate()
        );

        usuario.setNumeroCpf(rs.getLong("NUM_CPF"));

        return usuario;
    }
}
