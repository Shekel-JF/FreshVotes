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
    @Query("SELECT p FROM Product p " +
    "LEFT JOIN (SELECT f.product.id AS productId, COUNT(v) as total_upvotes " +
    "   FROM Feature f " +
    "   LEFT JOIN Vote v ON f.id = v.pk.feature.id AND v.upvote = true " +
    "   GROUP BY f.product.id) uf " +
    "ON p.id = uf.productId " +
    "GROUP BY p.id, p.name, p.published, p.user, COALESCE(uf.total_upvotes, 0) " +
    "ORDER BY COALESCE(uf.total_upvotes, 0) DESC " +
    "LIMIT 36")
    List<Product> findByUpvotes();
    Optional<Product> findByName(String name);
}