package com.example.RestAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDTO {
    private Long id;
    private String name;
    private Double price;

}
