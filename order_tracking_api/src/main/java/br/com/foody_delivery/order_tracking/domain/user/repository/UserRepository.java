package br.com.foody_delivery.order_tracking.domain.user.repository;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    Optional<User> findByEmailIgnoreCase(String email);

}
