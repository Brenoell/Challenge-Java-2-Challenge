
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.ComentarioDAO;
import br.com.fiap.soulupsociety.models.Comentario;

import java.time.LocalDate;
import java.util.List;

public class ComentarioService {

    private final ComentarioDAO dao = new ComentarioDAO();

    // Cadastrar comentário
    public void cadastrar(Comentario comentario) {

        if (comentario == null) {
            throw new IllegalArgumentException(
                    "Não foi possível cadastrar o comentário. Verifique os dados informados."
            );
        }

        if (comentario.getTextoComentario() == null ||
                comentario.getTextoComentario().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Digite um texto para publicar seu comentário."
            );
        }

        if (comentario.getConta() == null ||
                comentario.getConta().getId() == null ||
                comentario.getConta().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Não foi possível identificar sua conta. Faça login novamente e tente outra vez."
            );
        }

        if (comentario.getPostagem() == null ||
                comentario.getPostagem().getId() == null ||
                comentario.getPostagem().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Não foi possível identificar a publicação que você deseja comentar."
            );
        }

        if (comentario.getQuantidadeCurtidas() == null) {
            comentario.setQuantidadeCurtidas(0);
        }

        if (comentario.getQuantidadeCurtidas() < 0) {
            throw new IllegalArgumentException(
                    "A quantidade de curtidas não pode ser negativa."
            );
        }

        if (comentario.getDataComentario() == null) {
            comentario.setDataComentario(LocalDate.now());
        }

        dao.cadastrar(comentario);
    }

    // Consultar comentário
    public Comentario consultar(Integer id) {

        validarId(id);

        Comentario comentario = dao.consultarPorId(id);

        if (comentario == null) {
            throw new IllegalArgumentException(
                    "Não encontramos o comentário solicitado."
            );
        }

        return comentario;
    }

    // Listar comentários
    public List<Comentario> listar() {
        return dao.listar();
    }

    // Atualizar comentário
    public void atualizar(Comentario comentario) {

        if (comentario == null) {
            throw new IllegalArgumentException(
                    "Não foi possível atualizar o comentário. Verifique os dados informados."
            );
        }

        validarId(comentario.getId());

        if (comentario.getTextoComentario() == null ||
                comentario.getTextoComentario().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Digite um texto para atualizar seu comentário."
            );
        }

        if (comentario.getQuantidadeCurtidas() == null ||
                comentario.getQuantidadeCurtidas() < 0) {
            throw new IllegalArgumentException(
                    "A quantidade de curtidas informada é inválida."
            );
        }

        if (comentario.getDataComentario() == null) {
            throw new IllegalArgumentException(
                    "Não foi possível identificar a data do comentário."
            );
        }

        if (comentario.getConta() == null ||
                comentario.getConta().getId() == null ||
                comentario.getConta().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Não foi possível identificar a conta associada ao comentário."
            );
        }

        if (comentario.getPostagem() == null ||
                comentario.getPostagem().getId() == null ||
                comentario.getPostagem().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Não foi possível identificar a publicação associada ao comentário."
            );
        }

        // Confirma que o comentário existe antes de atualizá-lo.
        if (dao.consultarPorId(comentario.getId()) == null) {
            throw new IllegalArgumentException(
                    "Não encontramos o comentário que você deseja atualizar."
            );
        }

        dao.atualizar(comentario);
    }

    // Excluir comentário
    public void deletar(Integer id) {

        validarId(id);

        if (dao.consultarPorId(id) == null) {
            throw new IllegalArgumentException(
                    "Não encontramos o comentário que você deseja excluir."
            );
        }

        dao.deletar(id);
    }

    // Validar ID recebido nas operações
    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O identificador do comentário informado é inválido."
            );
        }
    }
}
