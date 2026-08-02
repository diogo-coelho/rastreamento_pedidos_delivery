package br.com.foody_delivery.order_tracking.infra.security;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import br.com.foody_delivery.order_tracking.exception.auth.AuthenticationGenerateTokenException;
import br.com.foody_delivery.order_tracking.exception.auth.AuthenticationVerificationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    private final String issuer = "Foody Delivery";
    private final String jwtTimezone;
    private final String jwtSecret;

    public JwtService(
            @Value("${jwt.timezone}") String jwtTimezone,
            @Value("${jwt.secret}") String jwtSecret
    ) {
        this.jwtTimezone = jwtTimezone;
        this.jwtSecret = jwtSecret;
    }

    public String generateJwtToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getUsername())
                    .withClaim("name", user.getName())
                    .withExpiresAt(addMinutesUntilExpiration(30))
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new AuthenticationGenerateTokenException(ex.getMessage());
        }
    }

    public String verifyToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();

            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException ex){
            throw new AuthenticationVerificationException(ex.getMessage());
        }
    }

    private Instant addMinutesUntilExpiration(Integer minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of(jwtTimezone));
    }
}
