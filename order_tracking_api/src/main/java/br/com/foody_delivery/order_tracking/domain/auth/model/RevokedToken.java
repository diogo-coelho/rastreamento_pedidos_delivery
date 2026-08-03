package br.com.foody_delivery.order_tracking.domain.auth.model;

import br.com.foody_delivery.order_tracking.infra.config.LocalDateTimeStringConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "revoked_tokens")
@Getter
@Setter
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String tokenHash;

    @Column(name = "expires_at")
    @Convert(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime expiresAt;

    public RevokedToken() {}

    public RevokedToken(String tokenHash, Instant expirationDate) {
        this.tokenHash = tokenHash;
        this.expiresAt = LocalDateTime.ofInstant(expirationDate, ZoneOffset.UTC);
    }
}
