package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.address.service.AddressService;
import br.com.foody_delivery.order_tracking.dto.address.AddressRequestDto;
import br.com.foody_delivery.order_tracking.dto.address.AddressResponseDto;
import br.com.foody_delivery.order_tracking.dto.error.ErrorResponseDto;
import br.com.foody_delivery.order_tracking.dto.user.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create/{userId}")
    @Operation(
            summary = "Cadastro de endereço",
            description = "Cria um novo endereço para um usuário existente na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Endereço cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = AddressResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para cadastro",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<AddressResponseDto> createAddressByUser(
            @PathVariable String userId,
            @Valid @RequestBody AddressRequestDto addressRequestDto
    ) {
        var address = addressService.createAddressByUser(userId, addressRequestDto);
        return ResponseEntity.ok(new AddressResponseDto(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.getNumber()
        ));
    }
}
