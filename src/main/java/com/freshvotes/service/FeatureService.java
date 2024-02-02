package com.freshvotes.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.FeatureRepository;
import com.freshvotes.repositories.UpvoteRepository;

@Service
public class FeatureService
{
    @Autowired
    private FeatureRepository featureRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private UpvoteRepository upvoteRepo;

    public Feature createFeature(Long productId, User user)
    {
        Feature feature = new Feature();

        Optional<Product> productOpt = productService.findById(productId);

        if(productOpt.isPresent())
        {
            Product product = productOpt.get();
            
            feature.setProduct(product);
            product.getFeatures().add(feature);

            feature.setUser(user);
            user.getFeatures().add(feature);

            feature.setStatus("Pending review");

            return save(feature);
        }
        return feature;
    }

    public void getFeature(User user, ModelMap model, Long featureId)
    {
        Optional<Feature> featureOpt = findById(featureId);
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
    }

    public void updateFeature(User user, Feature feature, Long productId, Long featureId)
    {
        if(feature.getUser() == null || feature.getUser().getId() == user.getId())
        {
            feature.setUser(user);
            save(feature);
        }
    }

    public void setFeatureStatus(User user, Long productId, Long featureId, String status)
    {
        Optional<Feature> featureOpt = findById(featureId);
        if(featureOpt.isPresent())
        {
            Feature feature = featureOpt.get();
            feature.setStatus(status);
            save(feature);
        }
    }

    public void deleteFeature(User user, Long productId, Long featureId)
    {
        Optional<Feature> featureOpt = findById(featureId);
        if(featureOpt.isPresent())
        {
            Feature feature = featureOpt.get();
            if(user.getId() == feature.getUser().getId())
            {
                upvoteRepo.deleteAllByFeatureId(featureId);
                deleteById(featureId);
            }
        }    
    }

    public Feature save(Feature feature)
    {
        return featureRepo.save(feature);
    }

    public Optional<Feature> findById(Long featureId)
    {
        return featureRepo.findById(featureId);
    }

    public void deleteById(Long featureId)
    {
        featureRepo.deleteById(featureId);
    }

    public Long countUpvotesForFeature(Long featureId)
    {
        return featureRepo.countUpvotesForFeature(featureId);
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
