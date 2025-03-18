package com.example.RestAPI.service;


import com.example.RestAPI.dto.UserWithItemsDTO;
import com.example.RestAPI.entities.User;
import com.example.RestAPI.exception.*;
import com.example.RestAPI.repository.ItemRepository;
import com.example.RestAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.RestAPI.dto.ItemResponse;
import com.example.RestAPI.dto.UserRequestDTO;
import com.example.RestAPI.dto.UserResponseDTO;

@Service("user_service")
@RequiredArgsConstructor
public class UserService {

    @Autowired
    @Qualifier("user_repo")
    private final UserRepository userRepository;

    @Autowired
    @Qualifier("item_repo")
    private final ItemRepository itemRepository;

    @Cacheable(value = "users", cacheManager = "usersCacheManager")
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getId(), 
                        user.getUserName(), 
                        user.getFirstName(), 
                        user.getLastName(), 
                        user.getEmail(), 
                        user.getRole()))
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "users", cacheManager = "usersCacheManager", allEntries = true)
    public UserResponseDTO getUserById(Long id) throws NotFoundUserException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException(id));

        return new UserResponseDTO(
                user.getId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole());
    }


    public UserWithItemsDTO getUserWithItems(Long userId) throws NotFoundUserException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException(userId));

        List<ItemResponse> items = itemRepository.findByUserId(userId)
                .stream()
                .map(item -> new ItemResponse(item.getId(), item.getName(), item.getPrice()))
                .collect(Collectors.toList());

        return UserWithItemsDTO.builder()
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .items(items)
                .build();
    }

    
    @Transactional
    @CacheEvict(value = "users", cacheManager = "usersCacheManager", allEntries = true)
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userRequestDTO.getPassword());

        User user = User.builder()
                .userName(userRequestDTO.getUserName())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .email(userRequestDTO.getEmail())
                .password(hashedPassword)
                .role(userRequestDTO.getRole())
                .build();

        user = userRepository.save(user);

        return new UserResponseDTO(
                user.getId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole());
    }

    @Transactional
    @CacheEvict(value = "users", cacheManager = "usersCacheManager", allEntries = true)
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) throws NotFoundUserException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundUserException(id));

        user.setUserName(userRequestDTO.getUserName());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());

        if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        user.setRole(userRequestDTO.getRole());

        user = userRepository.save(user);

        return new UserResponseDTO(
                user.getId(),
                user.getUserName(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole());
    }

    @Transactional
    @CacheEvict(value = "users", cacheManager = "usersCacheManager", allEntries = true)
    public void deleteUser(Long id) throws NotFoundUserException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundUserException(id);
        }
        userRepository.deleteById(id);
    }
}
