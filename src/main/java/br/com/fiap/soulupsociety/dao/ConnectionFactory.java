package br.com.fiap.soulupsociety.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection obterConexao() {
        Connection conexao = null;

        try {
            conexao = DriverManager.getConnection(
                    "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl",
                    "rm569961",
                    "030308"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conexao;
    }
}

//package br.com.fiap.soulupsociety.dao;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class ConnectionFactory {
//
//    public static Connection obterConexao() {
//        String url = System.getenv("SPRING_DATASOURCE_URL");
//        String usuario = System.getenv("SPRING_DATASOURCE_USERNAME");
//        String senha = System.getenv("SPRING_DATASOURCE_PASSWORD");
//
//        if (url == null || usuario == null || senha == null) {
//            throw new IllegalStateException(
//                    "Variáveis de ambiente do Oracle não configuradas."
//            );
//        }
//
//        try {
//            return DriverManager.getConnection(url, usuario, senha);
//        } catch (SQLException e) {
//            throw new RuntimeException(
//                    "Erro ao conectar ao banco Oracle.", e
//            );
//        }
//    }