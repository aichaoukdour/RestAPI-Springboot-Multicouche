package com.example.RestAPI.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemResponse {
    private Long id;
    private String name;
    private double price;
}