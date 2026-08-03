package br.com.foody_delivery.order_tracking.domain.address.model;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import br.com.foody_delivery.order_tracking.infra.config.LocalDateTimeStringConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    private String street;

    private Integer number;

    @NotNull
    private String city;

    @NotNull
    private String state;

    private String postalCode;

    @NotNull
    private String country;

    @Column(name = "created_at")
    @Convert(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Convert(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime updatedAt;

    public Address() {}

    public Address(
            @NotNull User user,
            @NotBlank String street,
            @NotNull Integer number,
            String postalCode,
            @NotBlank String city,
            @NotBlank String state,
            @NotBlank String country
    ) {
        this.user = user;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
