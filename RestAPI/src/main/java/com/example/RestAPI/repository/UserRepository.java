package com.example.RestAPI.repository;

import com.example.RestAPI.entities.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("user_repo")
public interface UserRepository extends JpaRepository<User, Long> {

    @Cacheable(value = "users", key = "#username")
    Optional<User> findByUserName(String username);

    @Cacheable(value = "users", key = "#email")
    Optional<User> findByEmail(String email);

}
