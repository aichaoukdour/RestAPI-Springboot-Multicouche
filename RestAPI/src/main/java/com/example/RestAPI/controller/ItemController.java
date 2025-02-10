package com.example.RestAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.RestAPI.dto.ItemDTO;
import com.example.RestAPI.service.ItemService;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access
@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @GetMapping
    public List<ItemDTO> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        ItemDTO itemDTO = itemService.getItemById(id);
        if (itemDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(itemDTO);
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(
                                              @RequestParam("name") String name,
                                              @RequestParam("price") Double price) throws IOException {
       
        ItemDTO itemDTO = new ItemDTO(null, name, price); // Créer un DTO avec le chemin d'image
        ItemDTO createdItem = itemService.createItem(itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, 
                                              @RequestParam("name") String name, 
                                              @RequestParam("price") Double price) throws IOException {
       
        
        ItemDTO itemDTO = new ItemDTO(id, name, price); // Créer un DTO avec le chemin d'image
        ItemDTO updatedItem = itemService.updateItem(id, itemDTO);
        
        if (updatedItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Handle item not found
        }

        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Handle item not found
        }
    }

  
}
