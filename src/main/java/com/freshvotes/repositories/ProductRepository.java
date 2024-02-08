package com.freshvotes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findByUser(User user);

    Optional<Product> findByName(String name);

    @Query("Select p FROM Product p WHERE p.published = true ORDER BY p.id DESC LIMIT 15")
    List<Product> findNew();

    @Query("SELECT p FROM Product p " +
    "LEFT JOIN (SELECT f.product.id AS productId, COUNT(v) as total_upvotes " +
    "   FROM Feature f " +
    "   LEFT JOIN Vote v ON f.id = v.pk.feature.id AND v.upvote = true " +
    "   GROUP BY f.product.id) uf " +
    "ON p.id = uf.productId " +
    "WHERE p.published = true " +
    "GROUP BY p.id, p.name, p.published, p.user, COALESCE(uf.total_upvotes, 0) " +
    "HAVING COALESCE(COUNT(uf.total_upvotes), 0) > 0 " +
    "ORDER BY COALESCE(uf.total_upvotes, 0) DESC " +
    "LIMIT 12")
    List<Product> findByUpvotes();
}