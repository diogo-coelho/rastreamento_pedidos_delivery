package br.com.foody_delivery.order_tracking.dto.order;

import br.com.foody_delivery.order_tracking.domain.order.model.OrderItem;
import br.com.foody_delivery.order_tracking.domain.order.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDto(
        @NotBlank String userId,
        @NotBlank String addressId,
        @NotNull List<OrderItemRequestDto> items
) {
}
