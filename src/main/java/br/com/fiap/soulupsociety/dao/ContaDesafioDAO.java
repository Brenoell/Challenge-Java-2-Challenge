package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.exception.ConflitoException;
import br.com.fiap.soulupsociety.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.models.ContaDesafio;
import br.com.fiap.soulupsociety.models.Desafio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaDesafioDAO {

    // READ - Verifica se a conta já concluiu o desafio
    public boolean existe(Integer idConta, Integer idDesafio) {

        String sql = "SELECT 1 FROM conta_desafio " +
                "WHERE CONTA_id_conta = ? AND DESAFIO_id_desafio = ?";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, idConta);
            ps.setInt(2, idDesafio);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar conclusão do desafio.", e);
        }
    }

    // CREATE - Registra a conclusão e credita os pontos na carteira
    // (as duas operações na mesma transação: ou fazem as duas, ou nenhuma)
    public void registrarConclusao(ContaDesafio conclusao) {

        String sqlInsert = "INSERT INTO conta_desafio " +
                "(CONTA_id_conta, DESAFIO_id_desafio, dt_conclusao, qtd_pontos_recebidos) " +
                "VALUES (?, ?, ?, ?)";

        String sqlPontos = "UPDATE carteira " +
                "SET qtd_pontos = qtd_pontos + ? " +
                "WHERE CONTA_id_conta = ?";

        try (Connection conexao = ConnectionFactory.obterConexao()) {

            conexao.setAutoCommit(false);

            try (PreparedStatement psInsert = conexao.prepareStatement(sqlInsert);
                 PreparedStatement psPontos = conexao.prepareStatement(sqlPontos)) {

                psInsert.setInt(1, conclusao.getConta().getId());
                psInsert.setInt(2, conclusao.getDesafio().getId());
                psInsert.setDate(3, Date.valueOf(conclusao.getDataConclusao()));
                psInsert.setInt(4, conclusao.getPontosRecebidos());
                psInsert.executeUpdate();

                psPontos.setInt(1, conclusao.getPontosRecebidos());
                psPontos.setInt(2, conclusao.getConta().getId());

                if (psPontos.executeUpdate() == 0) {
                    throw new RecursoNaoEncontradoException(
                            "Carteira da conta não encontrada."
                    );
                }

                conexao.commit();

            } catch (SQLException | RuntimeException e) {
                conexao.rollback();
                throw e;
            }

        } catch (SQLException e) {

            // ORA-00001: violação da restrição UNIQUE (já concluído)
            if (e.getErrorCode() == 1) {
                throw new ConflitoException(
                        "Esta conta já concluiu este desafio."
                );
            }

            throw new RuntimeException("Erro ao registrar conclusão do desafio.", e);
        }
    }

    // READ - Listar desafios concluídos por uma conta
    public List<ContaDesafio> listarPorConta(Integer idConta) {

        List<ContaDesafio> conclusoes = new ArrayList<>();

        String sql = "SELECT cd.id_conta_desafio, cd.dt_conclusao, " +
                "cd.qtd_pontos_recebidos, " +
                "d.id_desafio, d.nm_desafio, d.ds_desafio, d.qtd_pontos_dados " +
                "FROM conta_desafio cd " +
                "JOIN desafio d ON d.id_desafio = cd.DESAFIO_id_desafio " +
                "WHERE cd.CONTA_id_conta = ? " +
                "ORDER BY cd.dt_conclusao DESC, cd.id_conta_desafio DESC";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, idConta);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Conta conta = new Conta();
                    conta.setId(idConta);

                    Desafio desafio = new Desafio();
                    desafio.setId(rs.getInt("id_desafio"));
                    desafio.setNome(rs.getString("nm_desafio"));
                    desafio.setDescricaoDesafio(rs.getString("ds_desafio"));
                    desafio.setQtdPontos(rs.getInt("qtd_pontos_dados"));

                    ContaDesafio conclusao = new ContaDesafio();
                    conclusao.setId(rs.getInt("id_conta_desafio"));
                    conclusao.setConta(conta);
                    conclusao.setDesafio(desafio);
                    conclusao.setDataConclusao(
                            rs.getDate("dt_conclusao").toLocalDate()
                    );
                    conclusao.setPontosRecebidos(
                            rs.getInt("qtd_pontos_recebidos")
                    );

                    conclusoes.add(conclusao);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar desafios concluídos.", e);
        }

        return conclusoes;
    }
}