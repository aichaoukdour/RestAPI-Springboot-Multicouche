package com.example.RestAPI.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RestAPI.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

   
    @Cacheable(value = "items", key = "#id")
    Item findById(long id);
}

