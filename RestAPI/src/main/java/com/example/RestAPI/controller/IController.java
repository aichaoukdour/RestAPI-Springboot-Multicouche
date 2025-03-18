package com.example.RestAPI.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.example.RestAPI.exception.NotFoundUserException;


public interface IController<RequestDTO, ResponseDTO, ID> {

    List<ResponseDTO> getForUser();

    ResponseDTO getByID(ID id) throws NotFoundUserException;

    ResponseEntity<?> create(RequestDTO requestDTO, BindingResult bindingResult);

    ResponseEntity<?> update(ID id, RequestDTO requestDTO, BindingResult bindingResult) throws NotFoundUserException;

    void delete(ID id);
}
