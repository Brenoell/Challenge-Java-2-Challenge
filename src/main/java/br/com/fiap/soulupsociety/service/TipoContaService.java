package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.TipoContaDAO;
import br.com.fiap.soulupsociety.models.TipoConta;

import java.util.List;

public class TipoContaService {

    private TipoContaDAO dao = new TipoContaDAO();

    public void cadastrar(TipoConta tipoConta) {
        dao.cadastrar(tipoConta);
    }

    public TipoConta consultar(Integer id) {
        return dao.consultarPorId(id);
    }

    public List<TipoConta> listar() {
        return dao.listar();
    }

    public void atualizar(TipoConta tipoConta) {
        dao.atualizar(tipoConta);
    }

    public void deletar(Integer id) {
        dao.deletar(id);
    }
}