package com.example.RestAPI.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RestAPI.entities.Item;

@Repository("item_repo")
public interface ItemRepository extends JpaRepository<Item, Long> {

     @SuppressWarnings("null")
     @Cacheable(value = "items", key = "#id")
     Optional<Item> findById(Long id);

     List<Item> findByUserId(Long userId);

}
