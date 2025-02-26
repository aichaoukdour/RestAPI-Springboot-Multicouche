package com.example.RestAPI.controller;

import com.example.RestAPI.dto.UserDTO;
import com.example.RestAPI.dto.UserWithItemsDTO;
import com.example.RestAPI.entities.User;
import com.example.RestAPI.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController("user_controller")
@RequestMapping("api/v2/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    @Qualifier("user_service")
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<UserWithItemsDTO> getUserWithItems(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserWithItems(id));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        User createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok("User created successfully: " + createdUser.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        User updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok("User updated successfully: " + updatedUser.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
