
package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Comentario;
import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.models.Postagem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    // CREATE - Cadastrar comentário
    public void cadastrar(Comentario comentario) {

        String sql = "INSERT INTO COMENTARIO " +
                "(DS_COMENTARIO, DT_COMENTARIO, QTD_CURTIDAS, " +
                "IMG_COMENTARIO, CONTA_ID_CONTA, POSTAGEM_ID_POSTAGEM) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comentario.getTextoComentario());

            stmt.setDate(2, java.sql.Date.valueOf(
                    comentario.getDataComentario()
            ));

            stmt.setInt(3, comentario.getQuantidadeCurtidas());

            if (comentario.getImagem() == null) {
                stmt.setNull(4, java.sql.Types.BLOB);
            } else {
                stmt.setBytes(4, comentario.getImagem());
            }

            stmt.setInt(5, comentario.getConta().getId());
            stmt.setInt(6, comentario.getPostagem().getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar comentário.", e);
        }
    }

    // READ - Consultar comentário por ID
    public Comentario consultarPorId(Integer id) {

        String sql = "SELECT * FROM COMENTARIO WHERE ID_COMENTARIO = ?";

        try (Connection conn = ConnectionFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return montarComentario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar comentário.", e);
        }

        return null;
    }

    // READ - Listar todos os comentários
    public List<Comentario> listar() {

        String sql = "SELECT * FROM COMENTARIO";

        List<Comentario> comentarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                comentarios.add(montarComentario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar comentários.", e);
        }

        return comentarios;
    }

    // READ - Listar comentários de uma postagem
    public List<Comentario> listarPorPostagem(Integer idPostagem) {

        String sql = "SELECT cm.id_comentario, cm.ds_comentario, cm.dt_comentario, " +
                "cm.qtd_curtidas, cm.conta_id_conta, c.nm_conta " +
                "FROM comentario cm " +
                "JOIN conta c ON c.id_conta = cm.conta_id_conta " +
                "WHERE cm.postagem_id_postagem = ? " +
                "ORDER BY cm.id_comentario DESC";

        List<Comentario> comentarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPostagem);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Comentario comentario = new Comentario();

                    comentario.setId(rs.getInt("ID_COMENTARIO"));
                    comentario.setTextoComentario(rs.getString("DS_COMENTARIO"));
                    comentario.setDataComentario(
                            rs.getDate("DT_COMENTARIO").toLocalDate()
                    );
                    comentario.setQuantidadeCurtidas(
                            rs.getInt("QTD_CURTIDAS")
                    );

                    Conta conta = new Conta();

                    conta.setId(rs.getInt("CONTA_ID_CONTA"));
                    conta.setNomeConta(rs.getString("NM_CONTA"));

                    comentario.setConta(conta);

                    comentarios.add(comentario);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar comentários da postagem.", e
            );
        }

        return comentarios;
    }

    // UPDATE - Atualizar comentário
    public boolean atualizar(Comentario comentario) {

        String sql = "UPDATE COMENTARIO " +
                "SET DS_COMENTARIO = ? " +
                "WHERE ID_COMENTARIO = ?";

        try (Connection conn = ConnectionFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comentario.getTextoComentario());
            stmt.setInt(2, comentario.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar comentário.", e);
        }
    }

    // DELETE - Deletar comentário
    public boolean deletar(Integer id) {

        String sql = "DELETE FROM COMENTARIO WHERE ID_COMENTARIO = ?";

        try (Connection conn = ConnectionFactory.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar comentário.", e);
        }
    }

    // Método auxiliar para montar um comentário
    private Comentario montarComentario(ResultSet rs)
            throws SQLException {

        Comentario comentario = new Comentario();

        comentario.setId(rs.getInt("ID_COMENTARIO"));
        comentario.setTextoComentario(rs.getString("DS_COMENTARIO"));

        comentario.setDataComentario(
                rs.getDate("DT_COMENTARIO").toLocalDate()
        );

        comentario.setQuantidadeCurtidas(
                rs.getInt("QTD_CURTIDAS")
        );

        comentario.setImagem(rs.getBytes("IMG_COMENTARIO"));

        Conta conta = new Conta();
        conta.setId(rs.getInt("CONTA_ID_CONTA"));

        comentario.setConta(conta);

        Postagem postagem = new Postagem();
        postagem.setId(rs.getInt("POSTAGEM_ID_POSTAGEM"));

        comentario.setPostagem(postagem);

        return comentario;
    }
}
