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

    private final Connection conexao;
    private PreparedStatement ps = null;

    public UsuarioDAO() {
        conexao = ConnectionFactory.obterConexao();
    }

    public Usuario cadastrar(Usuario usuario) {

        String sql = "INSERT INTO usuario " +
                "(nm_usuario, dt_nascimento, num_cpf) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(
                sql, new String[]{"ID_USUARIO"})) {

            ps.setString(1, usuario.getNome());
            ps.setDate(2, Date.valueOf(usuario.getDataNascimento()));
            ps.setLong(3, usuario.getNumeroCpf());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }

            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário", e);
        }
    }

    public Usuario consultarPorId(Integer id) {

        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("nm_usuario"));
                usuario.setDataNascimento(
                        rs.getDate("dt_nascimento").toLocalDate()
                );
                usuario.setNumeroCpf(rs.getLong("num_cpf"));

                return usuario;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Usuario consultarPorCpf(Long cpf) {
        String sql = "SELECT * FROM USUARIO WHERE NUM_CPF = ?";

        try (Connection conn = ConnectionFactory.obterConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cpf);

            try (ResultSet rs = ps.executeQuery()) {
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
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar usuário pelo CPF.", e);
        }

        return null;
    }

    public List<Usuario> listar() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        try {
            ps = conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("nm_usuario"));
                usuario.setDataNascimento(
                        rs.getDate("dt_nascimento").toLocalDate()
                );
                usuario.setNumeroCpf(rs.getLong("num_cpf"));

                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return usuarios;
    }

    public void atualizar(Usuario usuario) {

        String sql = "UPDATE usuario SET " +
                "nm_usuario = ?, dt_nascimento = ?, num_cpf = ? " +
                "WHERE id_usuario = ?";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, usuario.getNome());
            ps.setDate(2, Date.valueOf(usuario.getDataNascimento()));
            ps.setLong(3, usuario.getNumeroCpf());
            ps.setInt(4, usuario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer id) {

        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}