package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.CarteiraDAO;
import br.com.fiap.soulupsociety.dao.PostagemDAO;
import br.com.fiap.soulupsociety.models.Postagem;

import java.time.LocalDate;
import java.util.List;

public class PostagemService {

    private final PostagemDAO dao = new PostagemDAO();
    private final CarteiraDAO carteiraDAO = new CarteiraDAO();

    private static final int PONTOS_POR_POSTAGEM = 100;

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

    public Postagem consultar(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Informe uma postagem válida."
            );
        }

        Postagem postagem = dao.consultarPorId(id);

        if (postagem == null) {
            throw new IllegalArgumentException(
                    "A postagem não foi encontrada."
            );
        }

        return postagem;
    }

    public List<Postagem> listar() {
        return dao.listar();
    }

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

        Postagem existente = dao.consultarPorId(postagem.getId());

        if (existente == null) {
            throw new IllegalArgumentException(
                    "A postagem que você deseja editar não foi encontrada."
            );
        }

        postagem.setDataPostagem(existente.getDataPostagem());
        postagem.setQuantidadeCurtidas(
                existente.getQuantidadeCurtidas()
        );
        postagem.setImagem(existente.getImagem());
        postagem.setConta(existente.getConta());

        dao.atualizar(postagem);
    }

    public void deletar(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Informe uma postagem válida para excluir."
            );
        }

        Postagem postagem = dao.consultarPorId(id);

        if (postagem == null) {
            throw new IllegalArgumentException(
                    "A postagem que você deseja excluir não foi encontrada."
            );
        }

        dao.deletar(id);
    }
}