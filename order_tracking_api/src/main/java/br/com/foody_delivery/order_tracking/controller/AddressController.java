package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.address.service.AddressService;
import br.com.foody_delivery.order_tracking.dto.address.AddressRequestDto;
import br.com.foody_delivery.order_tracking.dto.address.AddressResponseDto;
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
    public ResponseEntity<AddressResponseDto> createAddressByUser(
            @PathVariable String userId,
            @Valid @RequestBody AddressRequestDto addressRequestDto
    ) {
        var address = addressService.createAddressByUser(userId, addressRequestDto);
        return ResponseEntity.ok(new AddressResponseDto(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.getNumber()
        ));
    }
}
