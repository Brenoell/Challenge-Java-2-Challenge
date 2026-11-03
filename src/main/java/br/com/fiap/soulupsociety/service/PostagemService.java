
package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.CarteiraDAO;
import br.com.fiap.soulupsociety.dao.PostagemDAO;
import br.com.fiap.soulupsociety.exception.RecursoNaoEncontradoException;
import br.com.fiap.soulupsociety.models.Postagem;

import java.time.LocalDate;
import java.util.List;

public class PostagemService {

    private final PostagemDAO dao = new PostagemDAO();
    private final CarteiraDAO carteiraDAO = new CarteiraDAO();

    private static final int PONTOS_POR_POSTAGEM = 100;

    // CREATE - Cadastrar postagem
    public void cadastrar(Postagem postagem) {

        if (postagem == null) {
            throw new IllegalArgumentException(
                    "Não foi possível publicar a postagem."
            );
        }

        if (postagem.getDescricaoTexto() == null ||
                postagem.getDescricaoTexto().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Escreva um texto para publicar."
            );
        }

        if (postagem.getConta() == null ||
                postagem.getConta().getId() == null ||
                postagem.getConta().getId() <= 0) {
            throw new IllegalArgumentException(
                    "Não foi possível identificar a conta da postagem."
            );
        }

        postagem.setDataPostagem(LocalDate.now());
        postagem.setQuantidadeCurtidas(0);

        dao.cadastrar(postagem);

        carteiraDAO.adicionarPontosPorConta(
                postagem.getConta().getId(),
                PONTOS_POR_POSTAGEM
        );
    }

    // READ - Consultar postagem por ID
    public Postagem consultar(Integer id) {

        validarId(id);

        Postagem postagem = dao.consultarPorId(id);

        if (postagem == null) {
            throw new RecursoNaoEncontradoException(
                    "A postagem não foi encontrada."
            );
        }

        return postagem;
    }

    // READ - Listar postagens
    public List<Postagem> listar() {

        return dao.listar();
    }

    // UPDATE - Atualizar postagem
    public void atualizar(Postagem postagem) {

        if (postagem == null ||
                postagem.getId() == null ||
                postagem.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Informe uma postagem válida para atualizar."
            );
        }

        if (postagem.getDescricaoTexto() == null ||
                postagem.getDescricaoTexto().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O texto da postagem não pode ficar vazio."
            );
        }

        Postagem existente = consultar(postagem.getId());

        // Preserva os dados originais da postagem
        postagem.setDataPostagem(existente.getDataPostagem());

        postagem.setQuantidadeCurtidas(
                existente.getQuantidadeCurtidas()
        );

        postagem.setImagem(existente.getImagem());

        postagem.setConta(existente.getConta());

        boolean atualizado = dao.atualizar(postagem);

        if (!atualizado) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível encontrar a postagem para atualizar."
            );
        }
    }

    // DELETE - Deletar postagem
    public void deletar(Integer id) {

        validarId(id);

        // verifica se a postagem existe
        consultar(id);

        boolean deletado = dao.deletar(id);

        if (!deletado) {
            throw new RecursoNaoEncontradoException(
                    "Não foi possível encontrar a postagem para excluir."
            );
        }
    }

    // Método auxiliar para validar ID
    private void validarId(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Informe uma postagem válida."
            );
        }
    }
}
