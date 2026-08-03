package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.order.model.Order;
import br.com.foody_delivery.order_tracking.domain.order.service.OrderService;
import br.com.foody_delivery.order_tracking.dto.address.AddressResponseDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderItemResponseDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderRequestDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderResponseDto;
import br.com.foody_delivery.order_tracking.dto.user.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getOrder() {
        return "Order details";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid OrderRequestDto orderRequestDto
    ) {
        var order = orderService.createOrder(orderRequestDto);
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
