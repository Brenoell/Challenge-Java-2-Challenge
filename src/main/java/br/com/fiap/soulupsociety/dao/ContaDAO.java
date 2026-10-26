package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.models.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    private final Connection conexao;

    public ContaDAO() {
        conexao = ConnectionFactory.obterConexao();
    }

    public void cadastrar(Conta conta) {

        conta.setDataCriacao(LocalDate.now());

        String sql = "INSERT INTO conta " +
                "(nm_conta, ds_email, ds_senha, dt_criacao, ds_bio, " +
                "USUARIO_id_usuario) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, conta.getNomeConta());
            ps.setString(2, conta.getEmail());
            ps.setString(3, conta.getSenha());
            ps.setDate(4, Date.valueOf(conta.getDataCriacao()));
            ps.setString(5, conta.getBio());
            ps.setInt(6, conta.getUsuario().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar conta.", e);
        }
    }

    public Conta login(String email, String senha) {

        String sql = "SELECT * FROM conta " +
                "WHERE ds_email = ? AND ds_senha = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Conta conta = new Conta();

                    conta.setId(rs.getInt("id_conta"));
                    conta.setNomeConta(rs.getString("nm_conta"));
                    conta.setEmail(rs.getString("ds_email"));
                    conta.setDataCriacao(
                            rs.getDate("dt_criacao").toLocalDate()
                    );
                    conta.setBio(rs.getString("ds_bio"));

                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("USUARIO_id_usuario"));
                    conta.setUsuario(usuario);

                    return conta;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao realizar login.", e);
        }

        return null;
    }

    public Conta consultarPorId(Integer id) {

        String sql = "SELECT * FROM conta WHERE id_conta = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Conta conta = new Conta();

                    conta.setId(rs.getInt("id_conta"));
                    conta.setNomeConta(rs.getString("nm_conta"));
                    conta.setEmail(rs.getString("ds_email"));
                    conta.setDataCriacao(
                            rs.getDate("dt_criacao").toLocalDate()
                    );
                    conta.setBio(rs.getString("ds_bio"));

                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("USUARIO_id_usuario"));
                    conta.setUsuario(usuario);

                    return conta;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar conta.", e);
        }

        return null;
    }

    public List<Conta> listar() {

        List<Conta> contas = new ArrayList<>();

        String sql = "SELECT * FROM conta";

        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Conta conta = new Conta();

                conta.setId(rs.getInt("id_conta"));
                conta.setNomeConta(rs.getString("nm_conta"));
                conta.setEmail(rs.getString("ds_email"));
                conta.setDataCriacao(
                        rs.getDate("dt_criacao").toLocalDate()
                );
                conta.setBio(rs.getString("ds_bio"));

                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("USUARIO_id_usuario"));
                conta.setUsuario(usuario);

                contas.add(conta);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas.", e);
        }

        return contas;
    }

    public void atualizar(Conta conta) {

        String sql = "UPDATE conta SET " +
                "nm_conta = ?, ds_email = ?, ds_senha = ?, ds_bio = ?, " +
                "USUARIO_id_usuario = ? " +
                "WHERE id_conta = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, conta.getNomeConta());
            ps.setString(2, conta.getEmail());
            ps.setString(3, conta.getSenha());
            ps.setString(4, conta.getBio());
            ps.setInt(5, conta.getUsuario().getId());
            ps.setInt(6, conta.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conta.", e);
        }
    }

    public void deletar(Integer id) {

        String sql = "DELETE FROM conta WHERE id_conta = ?";

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta.", e);
        }
    }
}