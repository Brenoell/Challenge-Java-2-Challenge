
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

    public void cadastrar(Conta conta) {

        conta.setDataCriacao(LocalDate.now());

        String sqlConta = "INSERT INTO conta " +
                "(nm_conta, ds_email, ds_senha, dt_criacao, ds_bio, " +
                "USUARIO_id_usuario) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        String sqlCarteira = "INSERT INTO carteira " +
                "(qtd_pontos, qtd_vales_desconto, qtd_passagens, CONTA_id_conta) " +
                "VALUES (5000, 0, 0, ?)";

        try (Connection con = ConnectionFactory.obterConexao()) {

            con.setAutoCommit(false);

            try {
                int idConta;

                try (PreparedStatement ps = con.prepareStatement(
                        sqlConta, new String[]{"id_conta"})) {

                    ps.setString(1, conta.getNomeConta());
                    ps.setString(2, conta.getEmail());
                    ps.setString(3, conta.getSenha());
                    ps.setDate(4, Date.valueOf(conta.getDataCriacao()));
                    ps.setString(5, conta.getBio());
                    ps.setInt(6, conta.getUsuario().getId());

                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {

                        if (!rs.next()) {
                            throw new SQLException(
                                    "Não foi possível obter o ID da conta criada."
                            );
                        }

                        idConta = rs.getInt(1);
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(sqlCarteira)) {
                    ps.setInt(1, idConta);
                    ps.executeUpdate();
                }

                con.commit();

                conta.setId(idConta);

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar conta.", e);
        }
    }

    public Conta login(String email, String senha) {

        String sql = "SELECT * FROM conta " +
                "WHERE ds_email = ? AND ds_senha = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return montarConta(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao realizar login.", e);
        }

        return null;
    }

    public Conta consultarPorId(Integer id) {

        String sql = "SELECT * FROM conta WHERE id_conta = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return montarConta(rs);
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

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                contas.add(montarConta(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas.", e);
        }

        return contas;
    }

    public boolean atualizar(Conta conta) {

        String sql = "UPDATE conta SET " +
                "nm_conta = ?, ds_email = ?, ds_senha = ?, ds_bio = ?, " +
                "USUARIO_id_usuario = ? " +
                "WHERE id_conta = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, conta.getNomeConta());
            ps.setString(2, conta.getEmail());
            ps.setString(3, conta.getSenha());
            ps.setString(4, conta.getBio());
            ps.setInt(5, conta.getUsuario().getId());
            ps.setInt(6, conta.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conta.", e);
        }
    }

    public boolean deletar(Integer id) {

        String sqlCarteira =
                "DELETE FROM carteira WHERE CONTA_id_conta = ?";

        String sqlConta =
                "DELETE FROM conta WHERE id_conta = ?";

        try (Connection con = ConnectionFactory.obterConexao()) {

            con.setAutoCommit(false);

            try {

                try (PreparedStatement ps = con.prepareStatement(sqlCarteira)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                int linhasAfetadas;

                try (PreparedStatement ps = con.prepareStatement(sqlConta)) {
                    ps.setInt(1, id);
                    linhasAfetadas = ps.executeUpdate();
                }

                if (linhasAfetadas == 0) {
                    con.rollback();
                    return false;
                }

                con.commit();

                return true;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta.", e);
        }
    }

    private Conta montarConta(ResultSet rs) throws SQLException {

        Conta conta = new Conta();

        conta.setId(rs.getInt("id_conta"));
        conta.setNomeConta(rs.getString("nm_conta"));
        conta.setEmail(rs.getString("ds_email"));

        Date dataCriacao = rs.getDate("dt_criacao");

        if (dataCriacao != null) {
            conta.setDataCriacao(dataCriacao.toLocalDate());
        }

        conta.setBio(rs.getString("ds_bio"));

        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("USUARIO_id_usuario"));

        conta.setUsuario(usuario);

        return conta;
    }
}
