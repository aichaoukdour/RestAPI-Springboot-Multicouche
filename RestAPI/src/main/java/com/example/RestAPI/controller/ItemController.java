package com.example.RestAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.RestAPI.dto.ItemRequest;
import com.example.RestAPI.dto.ItemResponse;
import com.example.RestAPI.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController("item_controller")
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    @Autowired
    @Qualifier("item_service")
    private final ItemService itemService;

    @GetMapping("/{userId}")
    public List<ItemResponse> getItemsForUser(@PathVariable Long userId) {
        return itemService.getItemsForUser(userId);
    }

    @GetMapping("/{id}/{userId}")
    public ItemResponse getItemById(@PathVariable Long userId, @PathVariable Long id) {
        return itemService.getItemById(userId, id);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse createItem(@PathVariable Long userId, @Valid @RequestBody ItemRequest itemRequest) {
        return itemService.createItem(userId, itemRequest);
    }

    @PutMapping("/{id}/{userId}")
    public ItemResponse updateItem(@PathVariable Long userId, @PathVariable Long id,
            @Valid @RequestBody ItemRequest itemRequest) {
        return itemService.updateItem(userId, id, itemRequest);
    }

    @DeleteMapping("/{id}/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long userId, @PathVariable Long id) {
        itemService.deleteItem(userId, id);
    }
    
}
