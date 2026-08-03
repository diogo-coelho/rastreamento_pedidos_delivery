package br.com.foody_delivery.order_tracking.domain.item.service;

import br.com.foody_delivery.order_tracking.domain.item.model.Item;
import br.com.foody_delivery.order_tracking.domain.item.repository.ItemRepository;
import br.com.foody_delivery.order_tracking.dto.item.ItemRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item createItem(ItemRequestDto itemRequestDto) {
        var item = new Item(
                itemRequestDto.name(),
                itemRequestDto.description(),
                itemRequestDto.price(),
                itemRequestDto.imageUrl()
        );
        return itemRepository.save(item);
    }
}
