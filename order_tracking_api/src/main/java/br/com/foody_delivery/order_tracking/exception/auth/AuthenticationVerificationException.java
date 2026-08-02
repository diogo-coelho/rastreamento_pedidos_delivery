package br.com.foody_delivery.order_tracking.exception.auth;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint
public class AuthenticationVerificationException extends RuntimeException {
    public AuthenticationVerificationException(String message) {
        super("Erro ao verificar o token JWT de acesso: " + message);
    }
}
