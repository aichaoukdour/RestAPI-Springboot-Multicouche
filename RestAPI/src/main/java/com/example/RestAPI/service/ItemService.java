package com.example.RestAPI.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.example.RestAPI.dto.ItemDTO;
import com.example.RestAPI.model.Item;
import com.example.RestAPI.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // Caching the retrieval of all items
    @Cacheable("items")
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                    .map(item -> new ItemDTO(item.getId(), item.getName(), item.getPrice()))
                    .collect(Collectors.toList());
    }

    // Caching the retrieval of an item by ID
    @Cacheable(value = "items", key = "#id")
    public ItemDTO getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        return new ItemDTO(item.getId(), item.getName(), item.getPrice());
    }

    // Save a new item and update the cache
    @CachePut(value = "items", key = "#itemDTO.id")
    public ItemDTO createItem(ItemDTO itemDTO) {
        Item item = new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getPrice());
        item = itemRepository.save(item);
        return new ItemDTO(item.getId(), item.getName(), item.getPrice());
    }

    // Update an existing item and update the cache
    @CachePut(value = "items", key = "#id")
    public ItemDTO updateItem(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        item.setName(itemDTO.getName());
        item.setPrice(itemDTO.getPrice());
        item = itemRepository.save(item);
        return new ItemDTO(item.getId(), item.getName(), item.getPrice());
    }

    // Delete an item and evict from the cache
    @CacheEvict(value = "items", key = "#id")
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        itemRepository.delete(item);
    }
}
