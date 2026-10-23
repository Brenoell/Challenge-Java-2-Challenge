package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.PostagemDAO;
import br.com.fiap.soulupsociety.models.Postagem;

import java.util.List;

public class PostagemService {

    private PostagemDAO dao = new PostagemDAO();

    public void cadastrar(Postagem postagem) {
        dao.cadastrar(postagem);
    }

    public Postagem consultar(Integer id) {
        return dao.consultarPorId(id);
    }

    public List<Postagem> listar() {
        return dao.listar();
    }

    public void atualizar(Postagem postagem) {
        dao.atualizar(postagem);
    }

    public void deletar(Integer id) {
        dao.deletar(id);
    }
}