package br.com.foody_delivery.order_tracking.domain.order.repository;

import br.com.foody_delivery.order_tracking.domain.order.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {

}
