package com.example.RestAPI.mapper;

import com.example.RestAPI.dto.ItemRequest;
import com.example.RestAPI.dto.ItemResponse;
import com.example.RestAPI.entities.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toEntity(ItemRequest request);
    ItemResponse toResponse(Item entity);
    
}

