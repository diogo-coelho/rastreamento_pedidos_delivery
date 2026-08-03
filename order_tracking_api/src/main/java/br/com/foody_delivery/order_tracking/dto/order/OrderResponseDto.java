package br.com.foody_delivery.order_tracking.dto.order;

import br.com.foody_delivery.order_tracking.domain.order.model.OrderItem;
import br.com.foody_delivery.order_tracking.domain.order.model.OrderStatus;
import br.com.foody_delivery.order_tracking.dto.address.AddressResponseDto;
import br.com.foody_delivery.order_tracking.dto.user.UserResponseDto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDto(
        String id,
        UserResponseDto user,
        AddressResponseDto address,
        List<OrderItemResponseDto> items,
        BigDecimal totalPrice,
        OrderStatus status
) {
}
