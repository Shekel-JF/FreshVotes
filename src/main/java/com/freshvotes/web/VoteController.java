package com.freshvotes.web;

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
import com.freshvotes.service.VoteService;


@Controller
@RequestMapping("/products/{productId}/features")
public class VoteController
{
    @Autowired
    private VoteService voteService;

    @GetMapping("{featureId}/votes")
    public String getVotes(@AuthenticationPrincipal User user, ModelMap model, @PathVariable Long featureId)
    {
        voteService.getVotes(user, model, featureId);
        return "vote";
    }
    
    @PostMapping("{featureId}/votes")
    public String updateVotes(@AuthenticationPrincipal User user, Feature feature,
    @PathVariable Long productId, @PathVariable Long featureId,
    @RequestParam(value = "upvote", required = false) Boolean upvote)
    {
        voteService.updateVotes(user, feature, productId, featureId, upvote);
        return "redirect:/products/" + productId + "/features/" + featureId + "/votes";
    }
}
