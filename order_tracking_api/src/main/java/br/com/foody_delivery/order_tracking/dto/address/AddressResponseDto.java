package br.com.foody_delivery.order_tracking.dto.address;

public record AddressResponseDto (
        String street,
        String city,
        String state,
        String postalCode,
        String country,
        Integer number
) {
}
