package com.example.RestAPI.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.RestAPI.dto.ItemRequest;
import com.example.RestAPI.dto.ItemResponse;
import com.example.RestAPI.entities.Item;
import com.example.RestAPI.mapper.ItemMapper;
import com.example.RestAPI.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    private Item item;
    private ItemResponse itemResponse;

    @BeforeEach
    void setUp() {
        item = new Item(1L, "Test Item", 10.0);
        new ItemRequest("Test Item", 10.0);
        itemResponse = new ItemResponse(1L, "Test Item", 10.0);
    }

    @Test
    public void testGetItemById_ShouldReturnItemResponse_WhenItemExists() {
        // Arrange
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemMapper.toResponse(item)).thenReturn(itemResponse);

        // Act
        ItemResponse response = itemService.getItemById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Item", response.getName());
        assertEquals(10.0, response.getPrice());
    }

    @Test
    public void testGetItemById_ShouldThrowItemNotFoundException_WhenItemDoesNotExist() {
        // Arrange
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(javax.jcr.ItemNotFoundException.class, () -> itemService.getItemById(1L));
    }
}