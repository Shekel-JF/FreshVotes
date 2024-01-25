package com.freshvotes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.domain.Vote;
import com.freshvotes.domain.VoteId;

public interface UpvoteRepository extends JpaRepository<Vote, VoteId>
{
    List<Vote> findByPkUserAndPkFeature(User user, Feature feature);
}
