package com.example.RestAPI.service;

import com.example.RestAPI.dto.ItemRequest;
import com.example.RestAPI.dto.ItemResponse;
import com.example.RestAPI.mapper.ItemMapper;
import com.example.RestAPI.entities.Item;
import com.example.RestAPI.entities.User;
import com.example.RestAPI.repository.ItemRepository;
import com.example.RestAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.RestAPI.entities.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Supposons que l'email est utilisé comme identifiant
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<ItemResponse> getItemsForUser() {
        User user = getCurrentUser();
        List<Item> items = itemRepository.findByUserId(user.getId());
        return itemMapper.toResponseList(items);
    }

    public ItemResponse getItemById(Long id) {
        User user = getCurrentUser();
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        if (!item.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This item does not belong to you");
        }
        return itemMapper.toItemResponse(item);
    }

    public ItemResponse createItem(ItemRequest itemRequest) {
        User user = getCurrentUser();
        Item item = itemMapper.toEntity(itemRequest);
        item.setUser(user);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toItemResponse(savedItem);
    }

    public ItemResponse updateItem(Long id, ItemRequest itemRequest) {
        User user = getCurrentUser();
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        if (!item.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This item does not belong to you");
        }
        item.setName(itemRequest.getItemName());
        item.setPrice(itemRequest.getItemPrice());
        Item updatedItem = itemRepository.save(item);
        return itemMapper.toItemResponse(updatedItem);
    }

    public void deleteItem(Long id) {
        User user = getCurrentUser();
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        if (!item.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This item does not belong to you");
        }
        itemRepository.delete(item);
    }
}
