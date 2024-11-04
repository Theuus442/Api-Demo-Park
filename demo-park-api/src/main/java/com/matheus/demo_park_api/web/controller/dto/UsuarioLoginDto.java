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
public class UsuarioLoginDto {

    @NotBlank(message = "O e-mail não pode estar vazio!")
    @Email(message = "Formato do e-mail inválido!")
    private String username;

    @NotBlank
    @Size(min = 6, max = 30)
    private String password;

}
