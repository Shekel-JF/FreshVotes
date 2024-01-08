package com.freshvotes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findByUser(User user);
    Optional<Product> findByName(String name);
}