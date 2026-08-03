package br.com.foody_delivery.order_tracking.domain.order.service;

import br.com.foody_delivery.order_tracking.domain.address.repository.AddressRepository;
import br.com.foody_delivery.order_tracking.domain.item.model.Item;
import br.com.foody_delivery.order_tracking.domain.item.repository.ItemRepository;
import br.com.foody_delivery.order_tracking.domain.order.model.Order;
import br.com.foody_delivery.order_tracking.domain.order.model.OrderItem;
import br.com.foody_delivery.order_tracking.domain.order.model.OrderStatus;
import br.com.foody_delivery.order_tracking.domain.order.repository.OrderRepository;
import br.com.foody_delivery.order_tracking.domain.user.repository.UserRepository;
import br.com.foody_delivery.order_tracking.dto.order.OrderItemRequestDto;
import br.com.foody_delivery.order_tracking.dto.order.OrderRequestDto;
import br.com.foody_delivery.order_tracking.exception.address.AddressNotFoundException;
import br.com.foody_delivery.order_tracking.exception.item.ItemNotFoundException;
import br.com.foody_delivery.order_tracking.exception.user.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ItemRepository itemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, AddressRepository addressRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Order createOrder(OrderRequestDto orderRequestDto) {
        var user = userRepository.findById(orderRequestDto.userId())
                .orElseThrow(() -> new UserNotFoundException(orderRequestDto.userId()));

        var address = addressRepository.findByUserId(orderRequestDto.userId())
                .orElseThrow(() -> new AddressNotFoundException(orderRequestDto.userId()));

        var order = new Order();
        order.setAddress(address);
        order.setUser(user);
        order.setTotalPrice(calculateTotalPrice(orderRequestDto.items()));
        order.setStatus(OrderStatus.RECEBIDO);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        for (var dto : orderRequestDto.items()) {
            var item = itemRepository.findById(dto.itemId())
                    .orElseThrow(() -> new ItemNotFoundException("Item not found: " + dto.itemId()));

            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setQuantity(dto.quantity());
            orderItem.setUnitPrice(item.getPrice());
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());

            order.addItem(orderItem);
        }

        return orderRepository.save(order);
    }

    private BigDecimal calculateTotalPrice(List<OrderItemRequestDto> items) {
        return items.stream()
                .map(item -> itemRepository.findById(item.itemId())
                        .orElseThrow(() -> new ItemNotFoundException("Item not found: " + item.itemId()))
                        .getPrice()
                        .multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
