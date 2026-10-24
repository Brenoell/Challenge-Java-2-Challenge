package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.TipoConta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoContaDAO {

    private Connection conexao;
    private PreparedStatement ps = null;

    public TipoContaDAO() {
        conexao = ConnectionFactory.obterConexao();
    }

    public void cadastrar(TipoConta tipoConta) {

        String sql = "INSERT INTO tipo_conta (nm_tipo_conta) VALUES (?)";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, tipoConta.getNome());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TipoConta consultarPorId(Integer id) {

        String sql = "SELECT * FROM tipo_conta WHERE id_tipo_conta = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                TipoConta tipoConta = new TipoConta();

                tipoConta.setId(rs.getInt("id_tipo_conta"));
                tipoConta.setNome(rs.getString("nm_tipo_conta"));

                return tipoConta;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<TipoConta> listar() {

        List<TipoConta> tipos = new ArrayList<>();

        String sql = "SELECT * FROM tipo_conta";

        try {
            ps = conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TipoConta tipoConta = new TipoConta();

                tipoConta.setId(rs.getInt("id_tipo_conta"));
                tipoConta.setNome(rs.getString("nm_tipo_conta"));

                tipos.add(tipoConta);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tipos;
    }

    public void atualizar(TipoConta tipoConta) {

        String sql = "UPDATE tipo_conta SET nm_tipo_conta = ? " +
                "WHERE id_tipo_conta = ?";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setString(1, tipoConta.getNome());
            ps.setInt(2, tipoConta.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Integer id) {

        String sql = "DELETE FROM tipo_conta WHERE id_tipo_conta = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}