package br.com.foody_delivery.order_tracking.exception.order;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("Order not found: " + orderId);
    }
}
