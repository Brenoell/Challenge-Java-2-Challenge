package br.com.fiap.soulupsociety;

import br.com.fiap.soulupsociety.dao.ConnectionFactory;
import br.com.fiap.soulupsociety.dao.DesafioDAO;
import br.com.fiap.soulupsociety.models.Desafio;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;

@SpringBootTest
class SoulupsocietyApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testarConexaoOracle() {

		Connection conexao = ConnectionFactory.obterConexao();

		if (conexao != null) {
			System.out.println("Conectado ao Oracle!");
		}
	}

	@Test
	void testarCadastroDesafio() {

		Desafio desafio = new Desafio();

		desafio.setNome("Reciclagem");
		desafio.setDescricaoDesafio("Reciclar materiais");
		desafio.setQtdPontos(100);

		DesafioDAO desafioDAO = new DesafioDAO();

		desafioDAO.cadastrar(desafio);

		System.out.println("Desafio cadastrado!");
	}
}