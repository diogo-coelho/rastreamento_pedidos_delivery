package br.com.foody_delivery.order_tracking.domain.auth.service;

public interface RevokedTokenService {

    boolean existsByTokenHash(String token);

}
