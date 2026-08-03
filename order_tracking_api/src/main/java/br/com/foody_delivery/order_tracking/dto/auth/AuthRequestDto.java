package br.com.foody_delivery.order_tracking.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequestDto (
        @Schema(example = "joao@email.com", description = "E-mail unico do usuario")
        @Email()
        @NotBlank()
        String email,
        @Schema(example = "senha123", description = "Senha do usuario")
        @NotBlank()
        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        String password
)
{
}
