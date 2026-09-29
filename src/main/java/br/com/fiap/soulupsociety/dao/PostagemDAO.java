package br.com.fiap.soulupsociety.dao;

import br.com.fiap.soulupsociety.models.Postagem;

import java.util.List;

public class PostagemDAO {

    // Create / Insert - CRUD
    public void cadastrar(Postagem postagem) {

    }

    // Read / Select - CRUD
    public Postagem consultarPorId(Integer id) {
        return null;
    }

    // Lista todas as postagens do feed
    public List<Postagem> listarPostagens() {
        return null;
    }

    // Lista as postagens feitas por uma conta
    public List<Postagem> consultarPorAutor(String nomeConta) {
        return null;
    }

    // Delete - CRUD
    public void deletarPostagem(Integer id) {

    }
}