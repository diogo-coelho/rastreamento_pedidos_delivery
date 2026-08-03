package br.com.foody_delivery.order_tracking.dto.item;

import java.math.BigDecimal;

public record ItemResponseDto(
        String id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl
) {
}
