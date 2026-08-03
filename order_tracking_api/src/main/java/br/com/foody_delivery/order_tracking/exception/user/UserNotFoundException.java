package br.com.foody_delivery.order_tracking.exception.user;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("User not found with id: " + userId);
    }
}
