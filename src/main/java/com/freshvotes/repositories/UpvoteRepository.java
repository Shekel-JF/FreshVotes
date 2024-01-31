package com.freshvotes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.domain.Vote;
import com.freshvotes.domain.VoteId;


public interface UpvoteRepository extends JpaRepository<Vote, VoteId>
{
    Optional<Vote> findByPkUserAndPkFeature(User user, Feature feature);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.pk.feature.id = :featureId")
    void deleteAllByFeatureId(@Param("featureId") Long featureId);

}
