package br.com.foody_delivery.order_tracking.infra.exception;

import jakarta.servlet.annotation.HttpConstraint;

@HttpConstraint
public class LocalDateTimeStringConverterException extends RuntimeException {
    public LocalDateTimeStringConverterException(String message) {
        super(message);
    }
}
