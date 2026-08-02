package br.com.foody_delivery.order_tracking.exception;

import br.com.foody_delivery.order_tracking.dto.error.ErrorResponseDto;
import br.com.foody_delivery.order_tracking.exception.auth.AuthenticationGenerateTokenException;
import br.com.foody_delivery.order_tracking.exception.auth.AuthenticationVerificationException;
import br.com.foody_delivery.order_tracking.exception.user.EmailAlreadyExistsException;
import br.com.foody_delivery.order_tracking.exception.user.EmailNotFoundException;
import br.com.foody_delivery.order_tracking.infra.exception.LocalDateTimeStringConverterException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto(
                        ex.getMessage(),
                        HttpStatus.CONFLICT.value()
                ));
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailNotFound(EmailNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(
                        ex.getMessage(),
                        HttpStatus.NOT_FOUND.value()
                ));
    }

    @ExceptionHandler(AuthenticationGenerateTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthenticationGenerateToken(AuthenticationGenerateTokenException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(
                        ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }

    @ExceptionHandler(AuthenticationVerificationException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthenticationVerificationToken(AuthenticationVerificationException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDto(
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED.value()
                ));
    }

    @ExceptionHandler(LocalDateTimeStringConverterException.class)
    public ResponseEntity<ErrorResponseDto> handleLocalDateTimeStringConverterException(LocalDateTimeStringConverterException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()
                ));
    }

}