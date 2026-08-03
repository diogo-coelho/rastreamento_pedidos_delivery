package br.com.foody_delivery.order_tracking.domain.order.model;

import br.com.foody_delivery.order_tracking.infra.config.LocalDateTimeStringConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    private String imageUrl;

    @Column(name = "created_at")
    @Convert(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Convert(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime updatedAt;

}
