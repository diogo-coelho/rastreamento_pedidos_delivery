package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.user.service.UserService;
import br.com.foody_delivery.order_tracking.dto.error.ErrorResponseDto;
import br.com.foody_delivery.order_tracking.dto.user.UserRequestDto;
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
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastro de usuário", description = "Cria um novo usuário na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuario cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados invalidos para cadastro",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "E-mail ja cadastrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<UserResponseDto> register(
            @RequestBody @Valid UserRequestDto userRequestDto,
            UriComponentsBuilder uriComponentsBuilder) {
        var user = userService.register(userRequestDto);

        var uri = uriComponentsBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserResponseDto(user.getId(), user.getName(), user.getEmail()));
    }

}
