package br.com.foody_delivery.order_tracking.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDto(
        @Schema(example = "uuid-user-1", description = "Id do usuário")
        String id,
        @Schema(example = "Joao Silva", description = "Nome do usuario")
        String name,
        @Schema(example = "joao@email.com", description = "E-mail do usuario")
        String email
) {
}
