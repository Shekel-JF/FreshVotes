package com.freshvotes.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.freshvotes.domain.Comment;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.FeatureRepository;
import com.freshvotes.service.CommentService;

@Controller
@RequestMapping("/products/{productId}/features/{featureId}/comments")
public class CommentController
{
    @Autowired
    public CommentService commentService;

    @Autowired
    public FeatureRepository featureRepo;

    @GetMapping("")
    @ResponseBody
    public List<Comment> getComments(@PathVariable Long FeatureId)
    {
        return commentService.findByFeatureId(FeatureId);
    }

    @PostMapping("")
    public String postComment(@AuthenticationPrincipal User user, Comment rootComment,
    @PathVariable Long featureId, @PathVariable Long productId,
    @RequestParam(required=false) Long parentId, @RequestParam(required=false) String childCommentText)
    {      
        commentService.postComment(user, rootComment, featureId, productId, parentId, childCommentText);
        return "redirect:/products/" + productId + "/features/" + featureId;
    }

    @PostMapping("/delete")
    public String deleteComment(@AuthenticationPrincipal User user, @RequestParam Long commentId, 
    @PathVariable Long featureId, @PathVariable Long productId)
    {
        commentService.deleteComment(user, commentId, featureId, productId);
       
        return "redirect:/products/" + productId + "/features/" + featureId;
    }
}
