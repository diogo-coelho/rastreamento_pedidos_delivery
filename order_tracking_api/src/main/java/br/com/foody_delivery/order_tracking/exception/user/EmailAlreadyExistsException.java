package br.com.foody_delivery.order_tracking.exception.user;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint()
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("O email " + email + " já está em uso.");
    }
}
