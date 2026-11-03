
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.UsuarioDAO;
import br.com.fiap.soulupsociety.exception.RecursoNaoEncontradoException;
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
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado."
            );
        }

        return usuario;
    }

    public Usuario consultarPorCpf(Long cpf) {

        if (cpf == null || !cpfValido(cpf)) {
            throw new IllegalArgumentException("Informe um CPF válido.");
        }

        Usuario usuario = usuarioDAO.consultarPorCpf(cpf);

        if (usuario == null) {
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado."
            );
        }

        return usuario;
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

        consultarPorId(usuario.getId());

        boolean atualizado = usuarioDAO.atualizar(usuario);

        if (!atualizado) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível atualizar o usuário."
            );
        }
    }

    public void deletar(Integer id) {
        validarId(id);

        consultarPorId(id);

        boolean deletado = usuarioDAO.deletar(id);

        if (!deletado) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível excluir o usuário."
            );
        }
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
    }

    private boolean cpfValido(Long cpf) {
        String numero = String.valueOf(cpf);

        return numero.matches("\\d{11}");
    }
}
