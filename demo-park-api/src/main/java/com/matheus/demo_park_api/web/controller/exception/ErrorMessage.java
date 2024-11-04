package com.matheus.demo_park_api.web.controller.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL) // Inclui a propriedade apenas se não for nula
    private Map<String, String> errors;

    // Construtor para mensagens de erro sem erros de validação
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase(); // Inicializa o statusText
        this.message = message;
        this.errors = null; // Define como null se não houver erros
    }

    // Construtor para mensagens de erro com erros de validação
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this(request, status, message); // Chama o construtor anterior
        addErrors(result); // Adiciona os erros de validação
    }

    // Método para adicionar erros de validação
    private void addErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        if (!errorMap.isEmpty()) {
            this.errors = errorMap; // Atribui o mapa de erros apenas se não estiver vazio
        } else {
            this.errors = null; // Define como null se não houver erros
        }
    }
}
