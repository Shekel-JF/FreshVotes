package com.freshvotes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Feature;


public interface FeatureRepository extends JpaRepository<Feature, Long>
{

}