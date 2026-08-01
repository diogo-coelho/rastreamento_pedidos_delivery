package br.com.foody_delivery.order_tracking.exception.user;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint()
public class EmailAlreadyExists extends RuntimeException {
    public EmailAlreadyExists(String message) {
        super(message);
    }
}
