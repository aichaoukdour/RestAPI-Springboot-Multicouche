package com.example.RestAPI.service;

import com.example.RestAPI.dto.ItemRequest;
import com.example.RestAPI.dto.ItemResponse;
import com.example.RestAPI.mapper.ItemMapper;
import com.example.RestAPI.entities.Item;
import com.example.RestAPI.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public List<ItemResponse> getAllItems() {
        log.info("Fetching all items");
        return itemRepository.findAll().stream() // stream : This converts the list of Item objects into a stream. A stream allows you to process collections of data in a functional way. It enables you to chain operations like map(), filter()
                .map(itemMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ItemResponse getItemById(Long id) {
        log.info("Fetching item with ID: {}", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Item not found with ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
                });
        return itemMapper.toResponse(item);
    }

    public ItemResponse createItem(ItemRequest itemRequest) {
        log.info("Creating item with name: {}", itemRequest.getName());
        Item item = itemMapper.toEntity(itemRequest);
        Item savedItem = itemRepository.save(item);
        log.info("Item created with ID: {}", savedItem.getId());
        return itemMapper.toResponse(savedItem);
    }

    public ItemResponse updateItem(Long id, ItemRequest itemRequest) {
        log.info("Updating item with ID: {}", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Item not found with ID: {}", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
                });
        item.setName(itemRequest.getName());
        item.setPrice(itemRequest.getPrice());
        Item updatedItem = itemRepository.save(item);
        log.info("Item updated with ID: {}", id);
        return itemMapper.toResponse(updatedItem);
    }

    public void deleteItem(Long id) {
        log.info("Deleting item with ID: {}", id);
        if (!itemRepository.existsById(id)) {
            log.error("Item not found with ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
        log.info("Item deleted with ID: {}", id);
    }
}
