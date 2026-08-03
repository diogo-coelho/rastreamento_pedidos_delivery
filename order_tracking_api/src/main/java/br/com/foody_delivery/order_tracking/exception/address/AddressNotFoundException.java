package br.com.foody_delivery.order_tracking.exception.address;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint
public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String addressId) {
        super("Address not found with id: " + addressId);
    }
}
