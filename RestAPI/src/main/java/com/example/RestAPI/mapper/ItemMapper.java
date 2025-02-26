package com.example.RestAPI.mapper;

import com.example.RestAPI.dto.ItemRequest;
import com.example.RestAPI.dto.ItemResponse;
import com.example.RestAPI.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(source = "itemName", target = "name") 
    @Mapping(source = "itemPrice", target = "price") 
    Item toEntity(ItemRequest request);

    @Mapping(source = "name", target = "itemName") 
    @Mapping(source = "price", target = "itemPrice") 
    ItemResponse toItemResponse(Item item);

    List<ItemResponse> toResponseList(List<Item> items);

}
