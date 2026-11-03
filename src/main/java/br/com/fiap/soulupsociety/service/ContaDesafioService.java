package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.ContaDesafioDAO;
import br.com.fiap.soulupsociety.exception.ConflitoException;
import br.com.fiap.soulupsociety.models.Conta;
import br.com.fiap.soulupsociety.models.ContaDesafio;
import br.com.fiap.soulupsociety.models.Desafio;

import java.time.LocalDate;
import java.util.List;

public class ContaDesafioService {

    private final ContaDesafioDAO dao = new ContaDesafioDAO();
    private final ContaService contaService = new ContaService();
    private final DesafioService desafioService = new DesafioService();
    private final CarteiraService carteiraService = new CarteiraService();

    // Concluir desafio: registra a conclusão e credita os pontos na carteira
    public ContaDesafio concluir(Integer idConta, Integer idDesafio) {

        validarId(idConta, "conta");
        validarId(idDesafio, "desafio");

        // Lançam 404 se não existirem
        Conta conta = contaService.consultar(idConta);
        Desafio desafio = desafioService.consultar(idDesafio);
        carteiraService.consultarPorConta(idConta);

        if (dao.existe(idConta, idDesafio)) {
            throw new ConflitoException(
                    "Esta conta já concluiu este desafio."
            );
        }

        ContaDesafio conclusao = new ContaDesafio();
        conclusao.setConta(conta);
        conclusao.setDesafio(desafio);
        conclusao.setDataConclusao(LocalDate.now());
        conclusao.setPontosRecebidos(desafio.getQtdPontos());

        dao.registrarConclusao(conclusao);

        return conclusao;
    }

    // Listar desafios concluídos pela conta
    public List<ContaDesafio> listarPorConta(Integer idConta) {

        validarId(idConta, "conta");

        // Lança 404 se a conta não existir
        contaService.consultar(idConta);

        return dao.listarPorConta(idConta);
    }

    private void validarId(Integer id, String entidade) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O identificador da " + entidade + " informado é inválido."
            );
        }
    }
}