
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.UsuarioDAO;
import br.com.fiap.soulupsociety.models.Usuario;

import java.time.LocalDate;
import java.util.List;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Cadastrar usuário
    public void cadastrar(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Não foi possível realizar o cadastro. Verifique os dados informados."
            );
        }

        if (usuario.getNome() == null ||
                usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe seu nome para continuar."
            );
        }

        if (usuario.getDataNascimento() == null) {
            throw new IllegalArgumentException(
                    "Informe sua data de nascimento."
            );
        }

        if (usuario.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data de nascimento não pode ser futura."
            );
        }

        if (usuario.getNumeroCpf() == null ||
                !cpfValido(usuario.getNumeroCpf())) {
            throw new IllegalArgumentException(
                    "Informe um CPF válido."
            );
        }

        usuarioDAO.cadastrar(usuario);
    }

    // Consultar usuário por ID
    public Usuario consultarPorId(Integer id) {

        validarId(id);

        Usuario usuario = usuarioDAO.consultarPorId(id);

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Não encontramos o usuário solicitado."
            );
        }

        return usuario;
    }

    // Listar usuários
    public List<Usuario> listar() {
        return usuarioDAO.listar();
    }

    // Atualizar usuário
    public void atualizar(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Não foi possível atualizar o cadastro. Verifique os dados informados."
            );
        }

        validarId(usuario.getId());

        if (usuario.getNome() == null ||
                usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe seu nome para continuar."
            );
        }

        if (usuario.getDataNascimento() == null) {
            throw new IllegalArgumentException(
                    "Informe sua data de nascimento."
            );
        }

        if (usuario.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data de nascimento não pode ser futura."
            );
        }

        if (usuario.getNumeroCpf() == null ||
                !cpfValido(usuario.getNumeroCpf())) {
            throw new IllegalArgumentException(
                    "Informe um CPF válido."
            );
        }

        if (usuarioDAO.consultarPorId(usuario.getId()) == null) {
            throw new IllegalArgumentException(
                    "Não encontramos o usuário que você deseja atualizar."
            );
        }

        usuarioDAO.atualizar(usuario);
    }

    // Excluir usuário
    public void deletar(Integer id) {

        validarId(id);

        if (usuarioDAO.consultarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "Não encontramos o usuário que você deseja excluir."
            );
        }

        usuarioDAO.deletar(id);
    }

    // Validar ID recebido nas operações
    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O identificador do usuário informado é inválido."
            );
        }
    }

    // Validar CPF
    private boolean cpfValido(Long cpf) {

        if (cpf == null || cpf < 10000000000L ||
                cpf > 99999999999L) {
            return false;
        }

        String numero = String.valueOf(cpf);

        // Rejeita CPFs com todos os dígitos iguais
        if (numero.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(numero.charAt(i))
                    * (10 - i);
        }

        int resto = soma % 11;
        int primeiroDigito = resto < 2 ? 0 : 11 - resto;

        if (Character.getNumericValue(numero.charAt(9))
                != primeiroDigito) {
            return false;
        }

        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(numero.charAt(i))
                    * (11 - i);
        }

        resto = soma % 11;
        int segundoDigito = resto < 2 ? 0 : 11 - resto;

        return Character.getNumericValue(numero.charAt(10))
                == segundoDigito;
    }
}
