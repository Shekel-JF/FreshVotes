package com.freshvotes.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.domain.Vote;
import com.freshvotes.domain.VoteId;
import com.freshvotes.repositories.UpvoteRepository;
import com.freshvotes.service.FeatureService;


@Controller
@RequestMapping("/products/{productId}/features")
public class VoteController
{
    @Autowired
    private FeatureService featureService;

    @Autowired
    private UpvoteRepository upvoteRepo;


    @GetMapping("{featureId}/votes")
    public String getVotes(@AuthenticationPrincipal User user, ModelMap model, @PathVariable Long featureId)
    {
        Optional<Feature> featureOpt = featureService.findById(featureId);
        if(featureOpt.isPresent())
        {
            Feature feature = featureOpt.get();
            model.put("feature", feature);
            Optional<Vote> voteOpt = upvoteRepo.findByPkUserAndPkFeature(user, feature);
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
        }

        model.put("user", user);
        return "vote";
    }
    
    @PostMapping("{featureId}/votes")
    public String updateVotes(@AuthenticationPrincipal User user, Feature feature, @PathVariable Long productId, @PathVariable Long featureId, @RequestParam(value = "upvote", required = false) Boolean upvote)
    {
        Optional<Feature> featureOpt = featureService.findById(featureId);
        if(featureOpt.isPresent())
        {
            feature = featureOpt.get();
            Optional<Vote> voteOpt = upvoteRepo.findByPkUserAndPkFeature(user, feature);
            if(voteOpt.isPresent())
            {
                Vote vote = voteOpt.get();
                if(upvote == null)
                {
                    upvoteRepo.delete(vote);
                }
                else
                {
                    vote.setUpvote(upvote);
                    upvoteRepo.save(vote);
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
                upvoteRepo.save(vote);
            }
        }
        return "redirect:/products/" + productId + "/features/" + featureId + "/votes";
    }
}
