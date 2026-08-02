package br.com.foody_delivery.order_tracking.exception.user;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint
public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException(String email) {
        super("O email " + email + "não foi encontrado.");
    }
}
