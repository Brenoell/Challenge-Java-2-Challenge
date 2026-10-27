
        package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.UsuarioDAO;
import br.com.fiap.soulupsociety.models.Usuario;

import java.time.LocalDate;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrar(Usuario usuario) {
        if (usuario == null ||
                usuario.getNome() == null ||
                usuario.getNome().trim().isEmpty() ||
                usuario.getDataNascimento() == null ||
                usuario.getDataNascimento().isAfter(LocalDate.now()) ||
                usuario.getNumeroCpf() == null ||
                !cpfValido(usuario.getNumeroCpf())) {
            throw new IllegalArgumentException("Verifique os dados do usuário.");
        }

        usuarioDAO.cadastrar(usuario);
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

        Usuario usuario = usuarioDAO.consultarPorCpf(cpf);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
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
            throw new IllegalArgumentException("Verifique os dados do usuário.");
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
        if (cpf == null || cpf < 10000000000L ||
                cpf > 99999999999L) {
            return false;
        }

        String numero = String.valueOf(cpf);

        if (numero.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(numero.charAt(i)) * (10 - i);
        }

        int resto = soma % 11;
        int primeiroDigito = resto < 2 ? 0 : 11 - resto;

        if (Character.getNumericValue(numero.charAt(9)) != primeiroDigito) {
            return false;
        }

        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(numero.charAt(i)) * (11 - i);
        }

        resto = soma % 11;
        int segundoDigito = resto < 2 ? 0 : 11 - resto;

        return Character.getNumericValue(numero.charAt(10)) == segundoDigito;
    }
}
