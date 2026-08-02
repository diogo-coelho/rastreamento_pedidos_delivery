package br.com.foody_delivery.order_tracking.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDto (
        @Email()
        @NotBlank()
        String email,
        @NotBlank()
        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        String password
)
{
}
