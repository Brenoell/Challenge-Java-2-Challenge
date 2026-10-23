package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.DesafioDAO;
import br.com.fiap.soulupsociety.models.Desafio;

import java.util.List;

public class DesafioService {

    private DesafioDAO dao = new DesafioDAO();

    public void cadastrar(Desafio desafio) {
        dao.cadastrar(desafio);
    }

    public Desafio consultar(Integer id) {
        return dao.consultarPorId(id);
    }

    public List<Desafio> listar() {
        return dao.listar();
    }

    public void atualizar(Desafio desafio) {
        dao.atualizar(desafio);
    }

    public void deletar(Integer id) {
        dao.deletar(id);
    }
}