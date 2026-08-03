package br.com.foody_delivery.order_tracking.domain.address.repository;

import br.com.foody_delivery.order_tracking.domain.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, String> {


}
