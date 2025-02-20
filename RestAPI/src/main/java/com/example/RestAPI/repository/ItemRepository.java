package com.example.RestAPI.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RestAPI.entities.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

// This annotation tells the compiler to ignore null-related warnings (NullPointerException) in this method
@SuppressWarnings("null") 
/**
     * Caches the result of this method so that if the method is called again 
     * with the same parameters, the cached result is returned instead of 
     * executing the method again.
*/
@Cacheable(value = "items", key = "#id")

// optional :  It is used to avoid null checks and handle the absence of a value in a more elegant way.
Optional<Item> findById(Long id);


}

