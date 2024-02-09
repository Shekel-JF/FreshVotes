package com.freshvotes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.domain.Vote;
import com.freshvotes.domain.VoteId;
import com.freshvotes.repositories.VoteRepository;

@Service
public class VoteService
{
    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private FeatureService featureService;

    public void getVotes(User user, ModelMap model, Long featureId)
    {
        Optional<Feature> featureOpt = featureService.findById(featureId);
        if(featureOpt.isPresent())
        {
            Feature feature = featureOpt.get();
            model.put("feature", feature);
            Optional<Vote> voteOpt = findByPkUserAndPkFeature(user, feature);
            if(voteOpt.isPresent())
            {
                Vote vote = voteOpt.get();
                model.put("upvote", vote.getUpvote());
            }
            else
            {
                Boolean empty = null;
                model.put("upvote", empty);
            }   
            model.put("upvoteSum", featureService.countUpvotesForFeature(featureId));
            model.put("downvoteSum", featureService.countDownvotesForFeature(featureId)); 
        }
        model.put("user", user);
    }

    @PostMapping("{featureId}/votes")
    public String updateVotes(User user, Feature feature, Long productId, Long featureId, Boolean upvote)
    {
        Optional<Feature> featureOpt = featureService.findById(featureId);
        if(featureOpt.isPresent())
        {
            feature = featureOpt.get();
            Optional<Vote> voteOpt = findByPkUserAndPkFeature(user, feature);
            if(voteOpt.isPresent())
            {
                Vote vote = voteOpt.get();
                if(upvote == null)
                {
                    delete(vote);
                }
                else
                {
                    vote.setUpvote(upvote);
                    save(vote);
                }
            }
            else if(upvote !=null)
            {
                VoteId voteId = new VoteId();
                voteId.setUser(user);
                voteId.setFeature(feature);
                
                Vote vote = new Vote();
                vote.setUpvote(upvote);
                vote.setPk(voteId);
                save(vote);
            }
        }
        return "redirect:/products/" + productId + "/features/" + featureId + "/votes";
    }

    public void delete(Vote vote)
    {
        voteRepo.delete(vote);
    }

    public void save(Vote vote)
    {
        voteRepo.save(vote);
    }

    public void deleteAllByFeatureId(Long FeatureId)
    {
        voteRepo.deleteAllByFeatureId(FeatureId);
    }

    public Optional<Vote> findByPkUserAndPkFeature(User user, Feature feature)
    {
        return voteRepo.findByPkUserAndPkFeature(user, feature);
    }
}
