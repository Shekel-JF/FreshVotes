package com.freshvotes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freshvotes.domain.Feature;


public interface FeatureRepository extends JpaRepository<Feature, Long>
{
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.pk.feature.id = :featureId AND v.upvote = true")
    Long countUpvotesForFeature(Long featureId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.pk.feature.id = :featureId AND v.upvote = false")
    Long countDownvotesForFeature(Long featureId);

    @Query("SELECT f FROM Feature f " +
    "WHERE f.product.id = :productId " +
    "ORDER BY f.id DESC LIMIT 14")
    List<Feature> findNew(Long productId);

    @Query("SELECT f " +
    "FROM Feature f " +
    "LEFT JOIN Vote v ON f.id = v.pk.feature.id AND v.upvote = true " +
    "WHERE f.product.id = :productId " +
    "GROUP BY f.id " +
    "ORDER BY COALESCE(SUM(v.upvote), 0) DESC LIMIT 14")
    List<Feature> findByUpvotes(Long productId);
}