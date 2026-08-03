package br.com.foody_delivery.order_tracking.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItemRequestDto(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull Double price,
        @NotBlank String imageUrl
) {
}
