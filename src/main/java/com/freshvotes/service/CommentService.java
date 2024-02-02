package com.freshvotes.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.CommentRepository;

@Service
public class CommentService
{
    @Autowired
    public CommentRepository commentRepo;

    @Autowired
    public FeatureService featureService;

    public void postComment(User user, Comment rootComment,
    Long featureId, Long productId,
    Long parentId, String childCommentText)
    {      
        Optional<Feature> featureOpt = featureService.findById(featureId);   
        
        if(StringUtils.hasLength(rootComment.getText()))
        {
            populateCommentMetadata(user, featureOpt, rootComment);
            save(rootComment);
        }
        else if(parentId != null)
        {   
            Comment comment = new Comment();
            Optional<Comment> parentCommentOpt = findById(parentId);
            if(parentCommentOpt.isPresent())
            {
                comment.setComment(parentCommentOpt.get());
            }
            comment.setText(childCommentText);
            populateCommentMetadata(user, featureOpt, comment);
            save(comment);
        }     
    }

    public void deleteComment(User user, Long commentId, 
    Long featureId, Long productId)
    {
        Optional<Comment> commentOpt = findById(commentId);
        if(commentOpt.isPresent())
        {
            if(user.getId() == commentOpt.get().getUser().getId())
            {
                deleteById(commentId);
            }       
        }
    }

    private void populateCommentMetadata(User user, Optional<Feature> featureOpt, Comment comment)
    {
        if(featureOpt.isPresent())
        {
            comment.setFeature(featureOpt.get());
        }
        comment.setUser(user);
        comment.setCreateDate(new Date());
    }


    public List<Comment> findByFeatureId(Long featureId)
    {
        return commentRepo.findByFeatureId(featureId);
    }

    public Comment save(Comment comment)
    {
        return commentRepo.save(comment);
    }

    public Optional<Comment> findById(Long commentId)
    {
        return commentRepo.findById(commentId);
    }

    public void deleteById(Long commentId)
    {
        commentRepo.deleteById(commentId);
    }

    
}
