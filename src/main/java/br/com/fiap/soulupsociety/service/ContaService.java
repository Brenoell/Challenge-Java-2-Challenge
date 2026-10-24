package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.ContaDAO;
import br.com.fiap.soulupsociety.models.Conta;

import java.util.List;

public class ContaService {

    private ContaDAO dao = new ContaDAO();

    public void cadastrar(Conta conta) {
        dao.cadastrar(conta);
    }

    public Conta consultar(Integer id) {
        return dao.consultarPorId(id);
    }

    public List<Conta> listar() {
        return dao.listar();
    }

    public void atualizar(Conta conta) {
        dao.atualizar(conta);
    }

    public void deletar(Integer id) {
        dao.deletar(id);
    }
}