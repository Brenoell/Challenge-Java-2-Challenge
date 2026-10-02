package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Comentario;
import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.models.Postagem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    private Connection conexao;


    // Create / Insert - CRUD
    public void cadastrar(Comentario comentario) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "INSERT INTO COMENTARIO " +
                "(ID_COMENTARIO, DS_COMENTARIO, DT_COMENTARIO, " +
                "CONTA_IDCONTA, POSTAGEM_IDPOSTAGEM) " +
                "VALUES (SEQ_COMENTARIO.NEXTVAL, ?, ?, ?, ?)";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, comentario.getTextoComentario());
            ps.setDate(2, Date.valueOf(comentario.getDataComentario()));
            ps.setInt(3, comentario.getConta().getId());
            ps.setInt(4, comentario.getPostagem().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Read / Select - CRUD
    public Comentario consultarPorId(Integer id) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "SELECT * FROM COMENTARIO " +
                "WHERE ID_COMENTARIO = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Comentario comentario = new Comentario();

                comentario.setId(rs.getInt("ID_COMENTARIO"));
                comentario.setTextoComentario(
                        rs.getString("DS_COMENTARIO")
                );

                comentario.setDataComentario(
                        rs.getDate("DT_COMENTARIO").toLocalDate()
                );

                Conta conta = new Conta();
                conta.setId(
                        rs.getInt("CONTA_IDCONTA")
                );

                comentario.setConta(conta);

                Postagem postagem = new Postagem();
                postagem.setId(
                        rs.getInt("POSTAGEM_IDPOSTAGEM")
                );

                comentario.setPostagem(postagem);

                return comentario;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Consulta comentários pelo autor
    public List<Comentario> consultarPorAutor(String nomeConta) {

        conexao = ConnectionFactory.obterConexao();

        List<Comentario> comentarios = new ArrayList<>();

        String sql = "SELECT C.* FROM COMENTARIO C " +
                "INNER JOIN CONTA CO " +
                "ON C.CONTA_IDCONTA = CO.ID_CONTA " +
                "WHERE CO.NM_CONTA = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, nomeConta);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Comentario comentario = new Comentario();

                comentario.setId(
                        rs.getInt("ID_COMENTARIO")
                );

                comentario.setTextoComentario(
                        rs.getString("DS_COMENTARIO")
                );

                comentario.setDataComentario(
                        rs.getDate("DT_COMENTARIO").toLocalDate()
                );

                Conta conta = new Conta();
                conta.setId(
                        rs.getInt("CONTA_IDCONTA")
                );

                comentario.setConta(conta);

                Postagem postagem = new Postagem();
                postagem.setId(
                        rs.getInt("POSTAGEM_IDPOSTAGEM")
                );

                comentario.setPostagem(postagem);

                comentarios.add(comentario);
            }

            return comentarios;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Update - CRUD
    public void atualizarComentario(Comentario comentario) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "UPDATE COMENTARIO " +
                "SET DS_COMENTARIO = ? " +
                "WHERE ID_COMENTARIO = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(
                    1,
                    comentario.getTextoComentario()
            );

            ps.setInt(
                    2,
                    comentario.getId()
            );

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Delete - CRUD
    public void deletarComentario(Comentario comentario) {

        conexao = ConnectionFactory.obterConexao();

        String sql = "DELETE FROM COMENTARIO " +
                "WHERE ID_COMENTARIO = ?";

        try {

            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(
                    1,
                    comentario.getId()
            );

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}