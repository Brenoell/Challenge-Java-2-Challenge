
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.UsuarioDAO;
import br.com.fiap.soulupsociety.models.Usuario;

import java.time.LocalDate;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario cadastrar(Usuario usuario) {

        if (usuario == null ||
                usuario.getNome() == null ||
                usuario.getNome().trim().isEmpty() ||
                usuario.getDataNascimento() == null ||
                usuario.getDataNascimento().isAfter(LocalDate.now()) ||
                usuario.getNumeroCpf() == null ||
                !cpfValido(usuario.getNumeroCpf())) {

            throw new IllegalArgumentException(
                    "Verifique os dados do usuário."
            );
        }

        return usuarioDAO.cadastrar(usuario);
    }

    public Usuario consultarPorId(Integer id) {
        validarId(id);

        Usuario usuario = usuarioDAO.consultarPorId(id);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        return usuario;
    }

    public Usuario consultarPorCpf(Long cpf) {
        if (cpf == null || !cpfValido(cpf)) {
            throw new IllegalArgumentException("Informe um CPF válido.");
        }

        return usuarioDAO.consultarPorCpf(cpf);
    }

    public List<Usuario> listar() {
        return usuarioDAO.listar();
    }

    public void atualizar(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário inválido.");
        }

        validarId(usuario.getId());

        if (usuario.getNome() == null ||
                usuario.getNome().trim().isEmpty() ||
                usuario.getDataNascimento() == null ||
                usuario.getDataNascimento().isAfter(LocalDate.now()) ||
                usuario.getNumeroCpf() == null ||
                !cpfValido(usuario.getNumeroCpf())) {

            throw new IllegalArgumentException(
                    "Verifique os dados do usuário."
            );
        }

        if (usuarioDAO.consultarPorId(usuario.getId()) == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        usuarioDAO.atualizar(usuario);
    }

    public void deletar(Integer id) {
        validarId(id);

        if (usuarioDAO.consultarPorId(id) == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        usuarioDAO.deletar(id);
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
    }

    private boolean cpfValido(Long cpf) {
        String numero = String.valueOf(cpf);

        return numero.matches("\\d{11}") &&
                !numero.matches("(\\d)\\1{10}");
    }
}
