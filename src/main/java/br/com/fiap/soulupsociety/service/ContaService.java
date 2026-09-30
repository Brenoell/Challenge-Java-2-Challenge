package br.com.fiap.soulupsociety.service;

import br.com.fiap.soulupsociety.dao.ContaDAO;
import br.com.fiap.soulupsociety.models.Conta;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    private ContaDAO contaDAO;

    public ContaService() {
        contaDAO = new ContaDAO();
    }


    // Cadastrar conta
    public void cadastrarConta(Conta conta) {
        contaDAO.cadastrar(conta);
    }


    // Consultar conta pelo ID
    public Conta consultarPorId(Integer id) {
        return contaDAO.consultarPorId(id);
    }


    // Consultar conta pelo e-mail
    public Conta consultarPorEmail(String email) {
        return contaDAO.consultarPorEmail(email);
    }


    // Atualizar conta
    public void atualizarConta(Conta conta) {
        contaDAO.atualizarConta(conta);
    }


    // Deletar conta
    public void deletarConta(Integer id) {
        contaDAO.deletarConta(id);
    }
}