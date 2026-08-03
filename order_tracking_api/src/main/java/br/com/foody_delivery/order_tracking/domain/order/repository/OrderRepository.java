package br.com.foody_delivery.order_tracking.domain.order.repository;

import br.com.foody_delivery.order_tracking.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {


}
