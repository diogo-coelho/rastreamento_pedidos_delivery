package br.com.foody_delivery.order_tracking.dto.order;

import br.com.foody_delivery.order_tracking.domain.item.model.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        @NotBlank String name,
        @NotBlank String imageUrl,
        @NotNull BigDecimal price,
        @NotNull Integer quantity
) {
}
