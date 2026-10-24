package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.UsuarioDAO;
import br.com.fiap.soulupsociety.models.Usuario;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrar(Usuario usuario) {
        usuarioDAO.cadastrar(usuario);
    }

    public Usuario consultarPorId(Integer id) {
        return usuarioDAO.consultarPorId(id);
    }

    public List<Usuario> listar() {
        return usuarioDAO.listar();
    }

    public void atualizar(Usuario usuario) {
        usuarioDAO.atualizar(usuario);
    }

    public void deletar(Integer id) {
        usuarioDAO.deletar(id);
    }
}