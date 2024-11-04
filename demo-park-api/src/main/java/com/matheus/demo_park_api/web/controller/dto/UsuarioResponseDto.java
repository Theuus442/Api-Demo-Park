package com.matheus.demo_park_api.web.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioResponseDto {

    private Long id;            // Identificador único do usuário
    private String username;      // Renomeado para "email" para maior clareza.
    private String role;       // Função ou papel do usuário no sistema
}
