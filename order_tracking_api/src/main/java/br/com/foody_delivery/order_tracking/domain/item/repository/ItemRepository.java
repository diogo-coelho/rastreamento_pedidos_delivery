package br.com.foody_delivery.order_tracking.domain.item.repository;

import br.com.foody_delivery.order_tracking.domain.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {

}
