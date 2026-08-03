package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.order.model.Order;
import br.com.foody_delivery.order_tracking.domain.order.model.OrderStatus;
import br.com.foody_delivery.order_tracking.domain.order.service.OrderService;
import br.com.foody_delivery.order_tracking.dto.address.AddressResponseDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderItemResponseDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderRequestDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderResponseDto;
import br.com.foody_delivery.order_tracking.dto.order.UpdateStatusRequestDto;
import br.com.foody_delivery.order_tracking.dto.user.UserResponseDto;
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
    public ResponseEntity<List<OrderResponseDto>> getOrder() {
        var orders = orderService.getAllOrders();
        var orderResponseList = orders.stream()
                .map(this::convertToOrderResponseDto)
                .toList();
        return ResponseEntity.ok(orderResponseList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String orderId) {
        var order = orderService.getOrderById(orderId);
        var orderResponseDto = convertToOrderResponseDto(order);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid OrderRequestDto orderRequestDto
    ) {
        var order = orderService.createOrder(orderRequestDto);
        var orderResponseDto = convertToOrderResponseDto(order);
        return ResponseEntity.ok(orderResponseDto);
    }

    @PatchMapping("/update-status/{orderId}")
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
                order.getStatus()
        );
    }

}
