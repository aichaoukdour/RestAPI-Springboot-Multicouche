package com.example.RestAPI.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.RestAPI.dto.ItemRequest;
import com.example.RestAPI.dto.ItemResponse;
import com.example.RestAPI.service.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // Accès en lecture pour les rôles USER et ADMIN
    @GetMapping
    public List<ItemResponse> getAllItems() {
        log.info("Fetching all items");
        return itemService.getAllItems();
    }

    // Read Only for Admin , user and manager
    @GetMapping("/{id}")
    public ItemResponse getItemById(@PathVariable Long id) {
        log.info("Fetching item with ID: {}", id);
        return itemService.getItemById(id);
    }

    // Write permission for our  ADMIN
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse createItem(@RequestBody ItemRequest itemRequest) {
        log.info("Creating item with name: {}", itemRequest.getName());
        return itemService.createItem(itemRequest);
    }

    // Edit permission for admin only
    @PutMapping("/update/{id}")
    public ItemResponse updateItem(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        log.info("Updating item with ID: {}", id);
        return itemService.updateItem(id, itemRequest);
    }

    // access to delete items for ADMIN et MANAGER
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long id) {
        log.info("Deleting item with ID: {}", id);
        itemService.deleteItem(id);
    }
}
