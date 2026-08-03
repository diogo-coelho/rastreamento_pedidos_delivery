package br.com.foody_delivery.order_tracking.domain.item.service;

import br.com.foody_delivery.order_tracking.domain.item.model.Item;
import br.com.foody_delivery.order_tracking.dto.item.ItemRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ItemService {

    List<Item> getItems();

    Item createItem(@Valid ItemRequestDto itemRequestDto);

}
