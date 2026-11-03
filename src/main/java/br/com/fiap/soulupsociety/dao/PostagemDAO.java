
package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.models.Postagem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostagemDAO {

    // CREATE - Cadastrar postagem
    public void cadastrar(Postagem postagem) {

        String sql = "INSERT INTO postagem " +
                "(ds_postagem, dt_postagem, qtd_curtidas, img_postagem, CONTA_id_conta) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, postagem.getDescricaoTexto());
            ps.setDate(2, Date.valueOf(postagem.getDataPostagem()));
            ps.setInt(3, postagem.getQuantidadeCurtidas());

            if (postagem.getImagem() == null) {
                ps.setNull(4, java.sql.Types.BLOB);
            } else {
                ps.setBytes(4, postagem.getImagem());
            }

            ps.setInt(5, postagem.getConta().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar postagem.", e);
        }
    }

    // READ - Consultar postagem por ID
    public Postagem consultarPorId(Integer id) {

        String sql = "SELECT p.*, c.nm_conta " +
                "FROM postagem p " +
                "JOIN conta c ON c.id_conta = p.CONTA_id_conta " +
                "WHERE p.id_postagem = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return montarPostagem(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar postagem.", e);
        }

        return null;
    }

    // READ - Listar todas as postagens
    public List<Postagem> listar() {

        List<Postagem> postagens = new ArrayList<>();

        String sql = "SELECT p.*, c.nm_conta " +
                "FROM postagem p " +
                "JOIN conta c ON c.id_conta = p.CONTA_id_conta " +
                "ORDER BY p.id_postagem DESC";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                postagens.add(montarPostagem(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar postagens.", e);
        }

        return postagens;
    }

    // UPDATE - Atualizar postagem
    public boolean atualizar(Postagem postagem) {

        String sql = "UPDATE postagem SET " +
                "ds_postagem = ?, dt_postagem = ?, qtd_curtidas = ?, " +
                "img_postagem = ?, CONTA_id_conta = ? " +
                "WHERE id_postagem = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, postagem.getDescricaoTexto());
            ps.setDate(2, Date.valueOf(postagem.getDataPostagem()));
            ps.setInt(3, postagem.getQuantidadeCurtidas());

            if (postagem.getImagem() == null) {
                ps.setNull(4, java.sql.Types.BLOB);
            } else {
                ps.setBytes(4, postagem.getImagem());
            }

            ps.setInt(5, postagem.getConta().getId());
            ps.setInt(6, postagem.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar postagem.", e);
        }
    }

    // DELETE - Deletar postagem
    public boolean deletar(Integer id) {

        String sql = "DELETE FROM postagem WHERE id_postagem = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar postagem.", e);
        }
    }

    // Método auxiliar para montar uma postagem
    private Postagem montarPostagem(ResultSet rs)
            throws SQLException {

        Postagem postagem = new Postagem();

        postagem.setId(rs.getInt("id_postagem"));
        postagem.setDescricaoTexto(rs.getString("ds_postagem"));

        postagem.setDataPostagem(
                rs.getDate("dt_postagem").toLocalDate()
        );

        postagem.setQuantidadeCurtidas(
                rs.getInt("qtd_curtidas")
        );

        postagem.setImagem(
                rs.getBytes("img_postagem")
        );

        Conta conta = new Conta();

        conta.setId(rs.getInt("CONTA_id_conta"));
        conta.setNomeConta(rs.getString("nm_conta"));

        postagem.setConta(conta);

        return postagem;
    }
}
