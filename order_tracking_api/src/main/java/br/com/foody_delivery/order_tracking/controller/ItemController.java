package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.item.service.ItemService;
import br.com.foody_delivery.order_tracking.dto.address.AddressResponseDto;
import br.com.foody_delivery.order_tracking.dto.error.ErrorResponseDto;
import br.com.foody_delivery.order_tracking.dto.item.ItemRequestDto;
import br.com.foody_delivery.order_tracking.dto.item.ItemResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    @Operation(
            summary = "Listagem de itens",
            description = "Retorna a lista de todos os itens disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Itens listados com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum item encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<List<ItemResponseDto>> getItems() {
        var items = itemService.getItems();
        var itemResponseList = items.stream()
                .map(item -> new ItemResponseDto(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getImageUrl()
                ))
                .toList();
        return ResponseEntity.ok(itemResponseList);
    }

    @PostMapping("/create")
    @Operation(
            summary = "Criação de item",
            description = "Cria um novo item na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Item criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ItemResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para criação de item",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody @Valid ItemRequestDto itemRequestDto) {
        var item = itemService.createItem(itemRequestDto);
        var itemResponse = new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getImageUrl()
        );
        return ResponseEntity.ok(itemResponse);
    }
}
