package com.freshvotes.web;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.FeatureService;


@Controller
@RequestMapping("/products/{productId}/features")
public class FeatureController
{
    @Autowired
    private FeatureService featureService;

    @PostMapping("")
    public String createFeature(@AuthenticationPrincipal User user, @PathVariable long productId)
    {
        Feature feature = featureService.createFeature(productId, user);
        return "redirect:/products/" + productId + "/features/" + feature.getId();
    }

    @GetMapping("{featureId}")
    public String getFeature(@AuthenticationPrincipal User user, ModelMap model, @PathVariable Long featureId)
    {
        Optional<Feature> featureOpt = featureService.findById(featureId);
        if(featureOpt.isPresent())
        {
            Feature feature = featureOpt.get();
            model.put("feature", feature);

            Set<Comment> commentsWithoutDuplicates = getCommentsWithoutDuplicates(0, new HashSet<Long>(), feature.getComments());
            model.put("thread", commentsWithoutDuplicates);
            model.put("rootComment", new Comment());
        }

        model.put("user", user);
        
        // TODO what if no featureid present
        return "feature";
    }

    @PostMapping("{featureId}")
    public String updateFeature(@AuthenticationPrincipal User user, Feature feature, @PathVariable Long productId, @PathVariable Long featureId)
    {
        feature.setUser(user);
        feature = featureService.save(feature);
         
        return "redirect:/products/" + productId + "/features/" + featureId;
    }
 
    private Set<Comment> getCommentsWithoutDuplicates(int page, Set<Long> visitedComments, Set<Comment> comments)
    {
        page++;
        Iterator<Comment> itr = comments.iterator();
        while(itr.hasNext())
        {
            Comment comment = itr.next();
            boolean addedToVisitedComments = visitedComments.add(comment.getId());
            
            if(!addedToVisitedComments)
            {
                itr.remove();
                if(page != 1)
                {
                    return comments;
                }           
            }
            else if(!comment.getComments().isEmpty())
            {
                getCommentsWithoutDuplicates(page, visitedComments, comment.getComments());
            }
            
        }
        return comments;
    }
}
