package com.example.RestAPI.controller.user;

import com.example.RestAPI.controller.IController;
import com.example.RestAPI.dto.UserRequestDTO;
import com.example.RestAPI.dto.UserResponseDTO;

interface IUserController extends IController<UserRequestDTO, UserResponseDTO, Long> {

}