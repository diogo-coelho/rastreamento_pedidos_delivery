package br.com.foody_delivery.order_tracking.domain.auth.repository;

import br.com.foody_delivery.order_tracking.domain.auth.model.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken, String> {

    boolean existsByTokenHash(String tokenHash);

}
