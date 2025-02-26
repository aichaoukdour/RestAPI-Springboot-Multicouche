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

@Slf4j
@Service("item_service")
@RequiredArgsConstructor
public class ItemService {

    @Autowired
    @Qualifier("item_repo")
    private final ItemRepository itemRepository;

    @Autowired
    @Qualifier("user_repo")
    private final UserRepository userRepository;

    @Autowired
    private final ItemMapper itemMapper;



    public List<ItemResponse> getItemsForUser(Long userId) {
        log.info("Fetching items for user with ID: {}", userId);
        userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
        List<Item> items = itemRepository.findByUserId(userId);
        return itemMapper.toResponseList(items); 
    }

   

    public ItemResponse getItemById(Long userId, Long id) {
        log.info("Fetching item with ID: {} for user {}", id, userId);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id));
        if (!item.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This item does not belong to the user");
        }

        return itemMapper.toItemResponse(item);
    }


    
    public ItemResponse createItem(Long userId, ItemRequest itemRequest) {
        log.info("Creating item for user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));
        Item item = itemMapper.toEntity(itemRequest);
        item.setUser(user);
        Item savedItem = itemRepository.save(item);
        return itemMapper.toItemResponse(savedItem);
    }


    

    public ItemResponse updateItem(Long userId, Long id, ItemRequest itemRequest) {
        log.info("Updating item with ID: {} for user {}", id, userId);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id));
        if (!item.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This item does not belong to the user");
        }

        item.setName(itemRequest.getItemName());
        item.setPrice(itemRequest.getItemPrice());
        Item updatedItem = itemRepository.save(item);

        return itemMapper.toItemResponse(updatedItem);
    }




    public void deleteItem(Long userId, Long id) {
        log.info("Deleting item with ID: {} for user {}", id, userId);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id));
        if (!item.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This item does not belong to the user");
        }

        itemRepository.delete(item);
    }

}