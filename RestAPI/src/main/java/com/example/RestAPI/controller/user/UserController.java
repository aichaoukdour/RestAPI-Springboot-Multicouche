package com.example.RestAPI.controller.user;

import com.example.RestAPI.dto.UserRequestDTO;
import com.example.RestAPI.dto.UserResponseDTO;
import com.example.RestAPI.dto.UserWithItemsDTO;
import com.example.RestAPI.exception.NotFoundUserException;
import com.example.RestAPI.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/users")
@RequiredArgsConstructor
public class UserController implements IUserController {

    private final UserService userService;

    @Override
    @GetMapping
    public List<UserResponseDTO> getForUser() {
        return userService.getAllUsers();
    }

    @Override
    @GetMapping("/{id}")
    public UserResponseDTO getByID(@PathVariable Long id) throws NotFoundUserException {
        return userService.getUserById(id);
    }

   
    @GetMapping("/{id}/items")
    public ResponseEntity<UserWithItemsDTO> getUserWithItems(@PathVariable Long id) throws NotFoundUserException {
        return ResponseEntity.ok(userService.getUserWithItems(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserRequestDTO userRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.ok("User created successfully: " + createdUser.getId());
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO, BindingResult bindingResult) throws NotFoundUserException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }

        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok("User updated successfully: " + updatedUser.getId());
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
        } catch (NotFoundUserException e) {
            e.printStackTrace();
        }
    }
}
