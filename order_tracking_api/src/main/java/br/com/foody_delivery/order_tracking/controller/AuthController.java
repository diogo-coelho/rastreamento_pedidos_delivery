package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.auth.service.AuthService;
import br.com.foody_delivery.order_tracking.dto.auth.AuthRequestDto;
import br.com.foody_delivery.order_tracking.dto.auth.AuthResponseDto;
import br.com.foody_delivery.order_tracking.dto.error.ErrorResponseDto;
import br.com.foody_delivery.order_tracking.dto.user.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AuthResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto dto) {
        var token = authService.login(dto.email(), dto.password());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout de usuário", description = "Realiza o logout de um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Logout realizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

}
