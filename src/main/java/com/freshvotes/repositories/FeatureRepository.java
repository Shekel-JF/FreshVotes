package com.freshvotes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.freshvotes.domain.Feature;


public interface FeatureRepository extends JpaRepository<Feature, Long>
{
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.pk.feature.id = :featureId AND v.upvote = true")
    Long countUpvotesForFeature(Long featureId);
}