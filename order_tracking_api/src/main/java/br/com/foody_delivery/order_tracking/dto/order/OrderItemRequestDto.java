package br.com.foody_delivery.order_tracking.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemRequestDto(
        @NotBlank String itemId,
        @NotNull Integer quantity
) {
}
