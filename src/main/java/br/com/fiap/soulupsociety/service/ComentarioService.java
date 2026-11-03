
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.CarteiraDAO;
import br.com.fiap.soulupsociety.dao.ComentarioDAO;
import br.com.fiap.soulupsociety.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulupsociety.models.Comentario;

import java.time.LocalDate;
import java.util.List;

public class ComentarioService {

    private final ComentarioDAO dao = new ComentarioDAO();
    private final CarteiraDAO carteiraDAO = new CarteiraDAO();

    private static final int PONTOS_POR_COMENTARIO = 50;

    // CREATE - Cadastrar comentário
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

    // READ - Consultar comentário por ID
    public Comentario consultar(Integer id) {

        validarId(id);

        Comentario comentario = dao.consultarPorId(id);

        if (comentario == null) {
            throw new RecursoNaoEncontradoException(
                    "Comentário não encontrado."
            );
        }

        return comentario;
    }

    // READ - Listar comentários
    public List<Comentario> listar() {

        return dao.listar();
    }

    // READ - Listar comentários de uma postagem
    public List<Comentario> listarPorPostagem(Integer idPostagem) {

        validarId(idPostagem);

        return dao.listarPorPostagem(idPostagem);
    }

    // UPDATE - Atualizar comentário
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

        Comentario existente = consultar(comentario.getId());

        // Mantém os dados originais do comentário
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

        boolean atualizado = dao.atualizar(comentario);

        if (!atualizado) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível encontrar o comentário para atualizar."
            );
        }
    }

    // DELETE - Deletar comentário
    public void deletar(Integer id) {

        validarId(id);

        // Verifica se o comentário existe
        consultar(id);

        boolean deletado = dao.deletar(id);

        if (!deletado) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível encontrar o comentário para excluir."
            );
        }
    }

    // Método auxiliar para validar IDs
    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Informe um ID válido."
            );
        }
    }
}
