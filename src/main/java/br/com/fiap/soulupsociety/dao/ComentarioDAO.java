package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Comentario;
import br.com.fiap.soulupsociety.models.Conta;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    private Connection conexao;
    private PreparedStatement ps = null;

    public ComentarioDAO() {
        conexao = ConnectionFactory.obterConexao();
    }

    public void cadastrar(Comentario comentario) {

        String sql = "INSERT INTO comentario " +
                "(ds_comentario, dt_comentario, qtd_curtidas, img_comentario, CONTA_id_conta) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, comentario.getTextoComentario());
            ps.setDate(2, Date.valueOf(comentario.getDataComentario()));
            ps.setInt(3, comentario.getQuantidadeCurtidas());
            ps.setBytes(4, comentario.getImagem());
            ps.setInt(5, comentario.getConta().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Comentario consultarPorId(Integer id) {

        String sql = "SELECT * FROM comentario WHERE id_comentario = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Comentario comentario = new Comentario();

                comentario.setId(rs.getInt("id_comentario"));
                comentario.setTextoComentario(
                        rs.getString("ds_comentario")
                );
                comentario.setDataComentario(
                        rs.getDate("dt_comentario").toLocalDate()
                );
                comentario.setQuantidadeCurtidas(
                        rs.getInt("qtd_curtidas")
                );
                comentario.setImagem(
                        rs.getBytes("img_comentario")
                );

                Conta conta = new Conta();
                conta.setId(rs.getInt("CONTA_id_conta"));
                comentario.setConta(conta);

                return comentario;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Comentario> listar() {

        List<Comentario> comentarios = new ArrayList<>();

        String sql = "SELECT * FROM comentario";

        try {
            ps = conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Comentario comentario = new Comentario();

                comentario.setId(rs.getInt("id_comentario"));
                comentario.setTextoComentario(
                        rs.getString("ds_comentario")
                );
                comentario.setDataComentario(
                        rs.getDate("dt_comentario").toLocalDate()
                );
                comentario.setQuantidadeCurtidas(
                        rs.getInt("qtd_curtidas")
                );
                comentario.setImagem(
                        rs.getBytes("img_comentario")
                );

                Conta conta = new Conta();
                conta.setId(rs.getInt("CONTA_id_conta"));
                comentario.setConta(conta);

                comentarios.add(comentario);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return comentarios;
    }

    public void atualizar(Comentario comentario) {

        String sql = "UPDATE comentario SET " +
                "ds_comentario = ?, dt_comentario = ?, qtd_curtidas = ?, " +
                "img_comentario = ?, CONTA_id_conta = ? " +
                "WHERE id_comentario = ?";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, comentario.getTextoComentario());
            ps.setDate(2, Date.valueOf(comentario.getDataComentario()));
            ps.setInt(3, comentario.getQuantidadeCurtidas());
            ps.setBytes(4, comentario.getImagem());
            ps.setInt(5, comentario.getConta().getId());
            ps.setInt(6, comentario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer id) {

        String sql = "DELETE FROM comentario WHERE id_comentario = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}