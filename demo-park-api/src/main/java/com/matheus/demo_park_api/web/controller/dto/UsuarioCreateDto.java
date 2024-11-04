package com.matheus.demo_park_api.web.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCreateDto {

    @NotBlank(message = "O e-mail não pode estar vazio!")
    @Email(message = "Formato do e-mail inválido!")
    private String Username;  // Renomeado para "Username" para maior clareza.

    @NotBlank(message = "A senha não pode estar vazia!")
    @Size(min = 6, max = 6, message = "A senha deve ter exatamente 6 caracteres.")
    private String password;
}
