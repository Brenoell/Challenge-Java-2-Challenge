package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.UsuarioDAO;
import br.com.fiap.soulupsociety.models.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    public void cadastrar(Usuario usuario) {
        if (usuario==null){
            throw new RuntimeException("Usuário não pode ser nulo");
        }

        if (usuario.getNome()==null){
            throw new RuntimeException("Inserção de nome é obrigatória.");
        }

        if (usuario.getNome().trim().length() < 3) {
            throw new RuntimeException("O nome deve possuir pelo menos 3 caracteres.");
        }

        if (usuario.getDataNascimento() == null){
            throw new RuntimeException("A data de nascimento é obrigatória.");
        }

        if (usuario.getDataNascimento().isAfter(LocalDate.now())) {
            throw new RuntimeException("A data de nascimento não pode estar no futuro.");
        }

        if (usuario.getNumeroCpf() == null || usuario.getNumeroCpf() <= 0){
            throw new RuntimeException("Inserção de Cpf é obrigatória.");
        }

        if (String.valueOf(usuario.getNumeroCpf()).length() != 11) {
            throw new RuntimeException("O CPF deve possuir 11 dígitos.");
        }

        if (usuarioDAO.consultarPorCpf(usuario.getNumeroCpf()) != null) {
            throw new RuntimeException("CPF já cadastrado.");
        }

        usuarioDAO.cadastrar(usuario);
    }

    public Usuario consultarPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("ID inválido.");
        }

        Usuario usuario = usuarioDAO.consultarPorId(id);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        return usuario;
    }

    public Usuario consultarPorCpf(long numeroCpf) {

        if (numeroCpf <= 0) {
            throw new RuntimeException("CPF inválido.");
        }

        Usuario usuario = usuarioDAO.consultarPorCpf(numeroCpf);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        return usuario;
    }

    public void atualizarUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new RuntimeException("Usuário inválido.");
        }

        if (usuario.getId() == null || usuario.getId() <= 0) {
            throw new RuntimeException("ID inválido.");
        }

        if (usuarioDAO.consultarPorId(usuario.getId()) == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório.");
        }

        if (usuario.getNome().trim().length() < 3) {
            throw new RuntimeException("O nome deve possuir pelo menos 3 caracteres.");
        }

        if (usuario.getDataNascimento() == null) {
            throw new RuntimeException("A data de nascimento é obrigatória.");
        }

        if (usuario.getDataNascimento().isAfter(LocalDate.now())) {
            throw new RuntimeException("A data de nascimento não pode estar no futuro.");
        }

        if (usuario.getNumeroCpf() <= 0) {
            throw new RuntimeException("CPF inválido.");
        }

        usuarioDAO.atualizarUsuario(usuario);
    }

    public void deletarUsuario(Integer id) {

        if (id == null || id <= 0) {
            throw new RuntimeException("ID inválido.");
        }

        Usuario usuario = usuarioDAO.consultarPorId(id);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        usuarioDAO.deletarUsuario(id);
    }
}