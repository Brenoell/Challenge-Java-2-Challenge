package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.CarteiraDAO;
import br.com.fiap.soulupsociety.models.Carteira;

import java.util.List;

public class CarteiraService {

    private CarteiraDAO dao = new CarteiraDAO();

    public void cadastrar(Carteira carteira) {
        dao.cadastrar(carteira);
    }

    public Carteira consultar(Integer id) {
        return dao.consultarPorId(id);
    }

    public List<Carteira> listar() {
        return dao.listar();
    }

    public void atualizar(Carteira carteira) {
        dao.atualizar(carteira);
    }

    public void deletar(Integer id) {
        dao.deletar(id);
    }
}