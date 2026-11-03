package br.com.fiap.soulupsociety.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {


    private final List<String> origensPermitidas = carregarOrigens();

    private static List<String> carregarOrigens() {
        String env = System.getenv("CORS_ALLOWED_ORIGINS");

        if (env == null || env.isBlank()) {
            return List.of("http://localhost:5173");
        }

        return Arrays.stream(env.split(","))
                .map(String::trim)
                .filter(o -> !o.isEmpty())
                .toList();
    }

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origem = request.getHeader("Origin");

        if (origem != null && origensPermitidas.contains(origem)) {

            response.setHeader("Access-Control-Allow-Origin", origem);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Vary", "Origin");


            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setHeader("Access-Control-Allow-Methods",
                        "GET, POST, PUT, PATCH, DELETE, OPTIONS");
                response.setHeader("Access-Control-Allow-Headers",
                        "Content-Type, Authorization, X-Requested-With");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        }

        chain.doFilter(req, res);
    }
}