package br.com.foody_delivery.order_tracking.domain.order.repository;

import br.com.foody_delivery.order_tracking.domain.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

}
