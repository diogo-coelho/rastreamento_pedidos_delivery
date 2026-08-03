package br.com.foody_delivery.order_tracking.dto.order;

import br.com.foody_delivery.order_tracking.domain.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequestDto(
        @NotNull OrderStatus status
) {
}
