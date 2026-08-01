package br.com.foody_delivery.order_tracking.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @Schema(example = "joao@email.com", description = "E-mail unico do usuario")
        @Email(message = "E-mail inválido")
        @NotBlank(message = "E-mail é obrigatório")
        String email,
        @Schema(example = "Joao Silva", description = "Nome completo do usuario")
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @Schema(example = "senha123", description = "Senha entre 6 e 25 caracteres")
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 25, message = "Senha deve ter entre 6 e 25 caracteres")
        String password
) {
}
