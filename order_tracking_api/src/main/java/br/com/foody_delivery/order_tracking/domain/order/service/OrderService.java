package br.com.foody_delivery.order_tracking.domain.order.service;

import br.com.foody_delivery.order_tracking.domain.order.model.Order;
import br.com.foody_delivery.order_tracking.domain.order.model.OrderStatus;
import br.com.foody_delivery.order_tracking.dto.order.OrderRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order createOrder(OrderRequestDto orderRequestDto);

    Order getOrderById(String orderId);

    Order updateOrderStatus(String orderId, @Valid OrderStatus status);
}
