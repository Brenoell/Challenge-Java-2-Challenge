package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.ComentarioDAO;
import br.com.fiap.soulupsociety.models.Comentario;

import java.util.List;

public class ComentarioService {

    private ComentarioDAO dao = new ComentarioDAO();

    public void cadastrar(Comentario comentario) {
        dao.cadastrar(comentario);
    }

    public Comentario consultar(Integer id) {
        return dao.consultarPorId(id);
    }

    public List<Comentario> listar() {
        return dao.listar();
    }

    public void atualizar(Comentario comentario) {
        dao.atualizar(comentario);
    }

    public void deletar(Integer id) {
        dao.deletar(id);
    }
}