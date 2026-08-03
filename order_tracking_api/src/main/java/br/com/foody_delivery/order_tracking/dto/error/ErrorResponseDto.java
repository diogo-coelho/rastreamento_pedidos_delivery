package br.com.foody_delivery.order_tracking.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponseDto(
        String message,
        Integer statusCode
) {
}
