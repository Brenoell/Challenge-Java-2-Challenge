
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.CarteiraDAO;
import br.com.fiap.soulupsociety.dao.ComentarioDAO;
import br.com.fiap.soulupsociety.models.Comentario;

import java.time.LocalDate;
import java.util.List;

public class ComentarioService {

    private final ComentarioDAO dao = new ComentarioDAO();
    private final CarteiraDAO carteiraDAO = new CarteiraDAO();

    private static final int PONTOS_POR_COMENTARIO = 50;

    public void cadastrar(Comentario comentario) {

        if (comentario == null) {
            throw new IllegalArgumentException(
                    "Não foi possível cadastrar o comentário."
            );
        }

        if (comentario.getTextoComentario() == null ||
                comentario.getTextoComentario().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O comentário não pode ficar vazio."
            );
        }

        if (comentario.getConta() == null ||
                comentario.getConta().getId() == null ||
                comentario.getConta().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Informe uma conta válida."
            );
        }

        if (comentario.getPostagem() == null ||
                comentario.getPostagem().getId() == null ||
                comentario.getPostagem().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Informe uma postagem válida."
            );
        }

        comentario.setDataComentario(LocalDate.now());
        comentario.setQuantidadeCurtidas(0);

        dao.cadastrar(comentario);

        carteiraDAO.adicionarPontosPorConta(
                comentario.getConta().getId(),
                PONTOS_POR_COMENTARIO
        );
    }

    public Comentario consultar(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Informe um comentário válido."
            );
        }

        Comentario comentario = dao.consultarPorId(id);

        if (comentario == null) {
            throw new IllegalArgumentException(
                    "Comentário não encontrado."
            );
        }

        return comentario;
    }

    public List<Comentario> listar() {
        return dao.listar();
    }

    public void atualizar(Comentario comentario) {

        if (comentario == null ||
                comentario.getId() == null ||
                comentario.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Informe um comentário válido para atualizar."
            );
        }

        if (comentario.getTextoComentario() == null ||
                comentario.getTextoComentario().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O comentário não pode ficar vazio."
            );
        }

        Comentario existente = dao.consultarPorId(
                comentario.getId()
        );

        if (existente == null) {
            throw new IllegalArgumentException(
                    "Comentário não encontrado."
            );
        }

        comentario.setDataComentario(
                existente.getDataComentario()
        );

        comentario.setQuantidadeCurtidas(
                existente.getQuantidadeCurtidas()
        );

        comentario.setImagem(
                existente.getImagem()
        );

        comentario.setConta(
                existente.getConta()
        );

        comentario.setPostagem(
                existente.getPostagem()
        );

        dao.atualizar(comentario);
    }

    public void deletar(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Informe um comentário válido para excluir."
            );
        }

        Comentario comentario = dao.consultarPorId(id);

        if (comentario == null) {
            throw new IllegalArgumentException(
                    "Comentário não encontrado."
            );
        }

        dao.deletar(id);
    }
}
