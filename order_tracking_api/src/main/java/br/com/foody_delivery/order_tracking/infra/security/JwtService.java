package br.com.foody_delivery.order_tracking.infra.security;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

public interface JwtService {

    String generateJwtToken(User user);

    String verifyToken(String token);

    String getRequestToken(HttpServletRequest httpServletRequest);

    Instant getExpirationDateFromToken(String token);

}
