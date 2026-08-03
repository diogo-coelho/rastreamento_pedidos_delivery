package br.com.foody_delivery.order_tracking.controller;

import br.com.foody_delivery.order_tracking.domain.item.service.ItemService;
import br.com.foody_delivery.order_tracking.dto.item.ItemRequestDto;
import br.com.foody_delivery.order_tracking.dto.item.ItemResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> getItems() {
        var items = itemService.getItems();
        var itemResponseList = items.stream()
                .map(item -> new ItemResponseDto(
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getImageUrl()
                ))
                .toList();
        return ResponseEntity.ok(itemResponseList);
    }

    @PostMapping("/create")
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody @Valid ItemRequestDto itemRequestDto) {
        var item = itemService.createItem(itemRequestDto);
        var itemResponse = new ItemResponseDto(
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getImageUrl()
        );
        return ResponseEntity.ok(itemResponse);
    }
}
