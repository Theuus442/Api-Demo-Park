package com.matheus.demoparkapi;

import com.matheus.demo_park_api.jwt.JwtToken;
import com.matheus.demo_park_api.web.controller.dto.UsuarioLoginDto;
import org.springframework.test.web.reactive.server.WebTestClient;

import org.springframework.http.HttpHeaders;
import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token;
        try {
            token = client
                    .post()
                    .uri("/api/v1/auth")
                    .bodyValue(new UsuarioLoginDto(username, password))
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(JwtToken.class)
                    .returnResult()
                    .getResponseBody()
                    .getToken();

            if (token == null) {
                throw new IllegalStateException("Token JWT não foi gerado. Verifique a autenticação.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Falha ao obter o token JWT. Verifique as credenciais ou o endpoint de autenticação.", e);
        }

        // Adiciona o token ao cabeçalho de autorização
        return headers -> {
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            System.out.println("Added Authorization Header: Bearer " + token); // Log do cabeçalho
        };
    }
}
