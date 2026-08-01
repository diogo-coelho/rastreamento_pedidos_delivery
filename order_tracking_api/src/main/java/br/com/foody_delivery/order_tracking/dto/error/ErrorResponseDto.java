package br.com.foody_delivery.order_tracking.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponseDto(
        @Schema(example = "E-mail já cadastrado")
        String message,
        @Schema(example = "409")
        Integer statusCode
) {
}
