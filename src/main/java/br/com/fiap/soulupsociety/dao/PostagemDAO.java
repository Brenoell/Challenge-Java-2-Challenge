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

    private Connection conexao;
    private PreparedStatement ps = null;

    public PostagemDAO() {
        conexao = ConnectionFactory.obterConexao();
    }

    public void cadastrar(Postagem postagem) {

        String sql = "INSERT INTO postagem " +
                "(ds_postagem, dt_postagem, qtd_curtidas, img_postagem, CONTA_id_conta) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, postagem.getDescricaoTexto());
            ps.setDate(2, Date.valueOf(postagem.getDataPostagem()));
            ps.setInt(3, postagem.getQuantidadeCurtidas());
            ps.setBytes(4, postagem.getImagem());
            ps.setInt(5, postagem.getConta().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Postagem consultarPorId(Integer id) {

        String sql = "SELECT p.*, c.nm_conta " +
                "FROM postagem p " +
                "JOIN conta c ON c.id_conta = p.CONTA_id_conta " +
                "WHERE p.id_postagem = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Postagem> listar() {

        List<Postagem> postagens = new ArrayList<>();

        String sql = "SELECT p.*, c.nm_conta " +
                "FROM postagem p " +
                "JOIN conta c ON c.id_conta = p.CONTA_id_conta";

        try {
            ps = conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

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

                postagens.add(postagem);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return postagens;
    }

    public void atualizar(Postagem postagem) {

        String sql = "UPDATE postagem SET " +
                "ds_postagem = ?, dt_postagem = ?, qtd_curtidas = ?, " +
                "img_postagem = ?, CONTA_id_conta = ? " +
                "WHERE id_postagem = ?";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, postagem.getDescricaoTexto());
            ps.setDate(2, Date.valueOf(postagem.getDataPostagem()));
            ps.setInt(3, postagem.getQuantidadeCurtidas());
            ps.setBytes(4, postagem.getImagem());
            ps.setInt(5, postagem.getConta().getId());
            ps.setInt(6, postagem.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer id) {

        String sql = "DELETE FROM postagem WHERE id_postagem = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}