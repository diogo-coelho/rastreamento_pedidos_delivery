package br.com.foody_delivery.order_tracking.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDto(
        @Email(message = "E-mail inválido")
        @NotBlank(message = "E-mail é obrigatório")
        String email,
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 12, message = "Senha deve ter entre 6 e 12 caracteres")
        String password
) {
}
