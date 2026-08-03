package br.com.foody_delivery.order_tracking.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRequestDto (
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String state,
        String postalCode,
        @NotBlank String country,
        @NotNull Integer number
) {
}
