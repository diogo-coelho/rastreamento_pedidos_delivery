package br.com.foody_delivery.order_tracking.domain.order.service;

import br.com.foody_delivery.order_tracking.domain.order.model.Order;
import br.com.foody_delivery.order_tracking.dto.order.OrderRequestDto;

public interface OrderService {

    Order createOrder(OrderRequestDto orderRequestDto);

}
