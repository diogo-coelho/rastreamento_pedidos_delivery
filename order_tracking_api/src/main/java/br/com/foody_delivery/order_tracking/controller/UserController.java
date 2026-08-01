package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.authentication.service.AuthenticationService;
import br.com.foody_delivery.order_tracking.dto.authentication.AuthRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return null;
    }

}
