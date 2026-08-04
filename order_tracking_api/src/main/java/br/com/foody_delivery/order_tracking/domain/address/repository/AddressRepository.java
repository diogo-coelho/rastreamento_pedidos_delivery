package br.com.foody_delivery.order_tracking.domain.address.repository;

import br.com.foody_delivery.order_tracking.domain.address.model.Address;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, String> {

}
