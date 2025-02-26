package com.example.RestAPI.service;

import com.example.RestAPI.dto.ItemResponse;
import com.example.RestAPI.dto.UserDTO;
import com.example.RestAPI.dto.UserWithItemsDTO;
import com.example.RestAPI.entities.User;
import com.example.RestAPI.repository.ItemRepository;
import com.example.RestAPI.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service("user_service")
@RequiredArgsConstructor
public class UserService {

    @Autowired
    @Qualifier("user_repo")
    private final UserRepository userRepository;

    @Autowired
    @Qualifier("item_repo")
    private final ItemRepository itemRepository;


    @Cacheable("users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @CacheEvict(value = "users", cacheManager = "usersCacheManager", allEntries = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
    }


    @Transactional
    @CacheEvict(value = "users", cacheManager = "usersCacheManager", allEntries = true)
    public User createUser(UserDTO userDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

        User user = User.builder()
                .userName(userDTO.getUserName())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(hashedPassword) 
                .role(userDTO.getRole()) 
                .build();

        return userRepository.save(user);
    }


    public UserWithItemsDTO getUserWithItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

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
    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user.setRole(userDTO.getRole());

        return userRepository.save(user);
    }


    @Transactional
    @CacheEvict(value = "users", cacheManager = "usersCacheManager", allEntries = true)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
    }
    
}
