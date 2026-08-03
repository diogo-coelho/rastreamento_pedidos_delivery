package br.com.foody_delivery.order_tracking.exception.item;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String itemId) {
        super("Item not found: " + itemId);
    }
}
