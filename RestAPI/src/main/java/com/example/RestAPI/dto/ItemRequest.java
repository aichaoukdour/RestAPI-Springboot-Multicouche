package com.example.RestAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemRequest {
    private Long id;
    private String name;
    private double price;
    public ItemRequest(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
}

