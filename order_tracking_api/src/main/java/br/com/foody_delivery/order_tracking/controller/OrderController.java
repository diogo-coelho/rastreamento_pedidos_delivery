package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.order.model.Order;
import br.com.foody_delivery.order_tracking.domain.order.model.OrderStatus;
import br.com.foody_delivery.order_tracking.domain.order.service.OrderService;
import br.com.foody_delivery.order_tracking.dto.address.AddressResponseDto;
import br.com.foody_delivery.order_tracking.dto.error.ErrorResponseDto;
import br.com.foody_delivery.order_tracking.dto.item.ItemResponseDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderItemResponseDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderRequestDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderResponseDto;
import br.com.foody_delivery.order_tracking.dto.order.UpdateStatusRequestDto;
import br.com.foody_delivery.order_tracking.dto.user.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(
            summary = "Listagem de pedidos",
            description = "Retorna a lista de todos os pedidos disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedidos listados com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum pedido encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<List<OrderResponseDto>> getOrder() {
        var orders = orderService.getAllOrders();
        var orderResponseList = orders.stream()
                .map(this::convertToOrderResponseDto)
                .toList();
        return ResponseEntity.ok(orderResponseList);
    }

    @GetMapping("/{orderId}")
    @Operation(
            summary = "Detalhes de um pedido",
            description = "Retorna os detalhes de um pedido específico.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String orderId) {
        var order = orderService.getOrderById(orderId);
        var orderResponseDto = convertToOrderResponseDto(order);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PostMapping("/create")
    @Operation(
            summary = "Criação de pedido",
            description = "Cria um novo pedido com base nas informações fornecidas.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido criado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para criação de pedido",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário ou endereço não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid OrderRequestDto orderRequestDto
    ) {
        var order = orderService.createOrder(orderRequestDto);
        var orderResponseDto = convertToOrderResponseDto(order);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PatchMapping("/{orderId}/status")
    @Operation(
            summary = "Atualização de status do pedido",
            description = "Atualiza o status de um pedido existente.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Status do pedido atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos para atualização de status",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
            )
    })
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable String orderId,
            @RequestBody @Valid UpdateStatusRequestDto updateStatusRequestDto
            ) {
        var order = orderService.updateOrderStatus(orderId, updateStatusRequestDto.status());
        var orderResponseDto = convertToOrderResponseDto(order);
        return ResponseEntity.ok(orderResponseDto);
    }

    private OrderResponseDto convertToOrderResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                new UserResponseDto(
                        order.getUser().getId(),
                        order.getUser().getName(),
                        order.getUser().getEmail()
                ),
                new AddressResponseDto(
                        order.getAddress().getId(),
                        order.getAddress().getStreet(),
                        order.getAddress().getCity(),
                        order.getAddress().getState(),
                        order.getAddress().getPostalCode(),
                        order.getAddress().getCountry(),
                        order.getAddress().getNumber()
                ),
                order.getItems().stream()
                        .map(item -> new OrderItemResponseDto(
                                item.getItem().getName(),
                                item.getItem().getImageUrl(),
                                item.getItem().getPrice(),
                                item.getQuantity()
                        ))
                        .toList(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

}
