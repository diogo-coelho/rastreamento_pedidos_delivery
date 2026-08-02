package br.com.foody_delivery.order_tracking.exception.auth;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint
public class AuthenticationGenerateTokenException extends RuntimeException {
    public AuthenticationGenerateTokenException(String message) {
        super("Erro ao tentar gerar o token JWT de acesso: " + message);
    }
}
