
package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Carteira;
import br.com.fiap.soulupsociety.models.Conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarteiraDAO {

    private Connection conexao;
    private PreparedStatement ps = null;

    public CarteiraDAO() {
        conexao = ConnectionFactory.obterConexao();
    }


    public void cadastrar(Carteira carteira) {

        String sql = "INSERT INTO carteira " +
                "(qtd_pontos, qtd_passagens, CONTA_id_conta) " +
                "VALUES (?, ?, ?)";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setInt(1, carteira.getQuantidadePontos());
            ps.setInt(2, carteira.getQuantidadePassagens());
            ps.setInt(3, carteira.getConta().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Carteira consultarPorId(Integer id) {

        String sql = "SELECT * FROM carteira WHERE id_carteira = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return montarCarteira(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    public List<Carteira> listar() {

        List<Carteira> carteiras = new ArrayList<>();

        String sql = "SELECT * FROM carteira";

        try {
            ps = conexao.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Carteira carteira = montarCarteira(rs);
                carteiras.add(carteira);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carteiras;
    }


    public void atualizar(Carteira carteira) {

        String sql = "UPDATE carteira SET " +
                "qtd_pontos = ?, " +
                "qtd_passagens = ?, " +
                "CONTA_id_conta = ? " +
                "WHERE id_carteira = ?";

        try {
            ps = conexao.prepareStatement(sql);

            ps.setInt(1, carteira.getQuantidadePontos());
            ps.setInt(2, carteira.getQuantidadePassagens());
            ps.setInt(3, carteira.getConta().getId());
            ps.setInt(4, carteira.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE - Deletar carteira
    public void deletar(Integer id) {

        String sql = "DELETE FROM carteira WHERE id_carteira = ?";

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Carteira montarCarteira(ResultSet rs) throws SQLException {

        Carteira carteira = new Carteira();

        carteira.setId(rs.getInt("id_carteira"));
        carteira.setQuantidadePontos(rs.getInt("qtd_pontos"));
        carteira.setQuantidadePassagens(rs.getInt("qtd_passagens"));

        Conta conta = new Conta();
        conta.setId(rs.getInt("CONTA_id_conta"));

        carteira.setConta(conta);

        return carteira;
    }
}
