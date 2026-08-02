package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.auth.service.AuthService;
import br.com.foody_delivery.order_tracking.dto.auth.AuthRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequestDto dto) {
        var token = authService.login(dto.email(), dto.password());
        return ResponseEntity.ok(token);
    }
}
