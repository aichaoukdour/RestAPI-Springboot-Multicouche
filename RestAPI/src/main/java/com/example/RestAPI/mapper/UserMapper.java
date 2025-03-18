package com.example.RestAPI.mapper;

import com.example.RestAPI.dto.UserRequestDTO;
import com.example.RestAPI.dto.UserResponseDTO;
import com.example.RestAPI.dto.UserWithItemsDTO;
import com.example.RestAPI.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserResponseDTO toUserResponseDTO(User user);
    User toEntity(UserRequestDTO userRequestDTO);

    @Mapping(source = "userItems", target = "items")
    UserWithItemsDTO toUserWithItemsDTO(User user);
}

