package br.com.foody_delivery.order_tracking.domain.order.model;

import br.com.foody_delivery.order_tracking.domain.item.model.Item;
import br.com.foody_delivery.order_tracking.infra.config.LocalDateTimeStringConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @NotNull
    private Item item;

    @NotNull
    private Integer quantity;

    @NotNull
    private BigDecimal unitPrice;

    @Column(name = "created_at")
    @Convert(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Convert(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime updatedAt;

}
