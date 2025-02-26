package com.example.RestAPI.mapper;

import com.example.RestAPI.dto.UserDTO;
import com.example.RestAPI.dto.UserWithItemsDTO;
import com.example.RestAPI.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);

    User toEntity(UserDTO userDTO);

    @Mapping(source = "userItems", target = "items")
    UserWithItemsDTO toUserWithItemsDTO(User user);

}
