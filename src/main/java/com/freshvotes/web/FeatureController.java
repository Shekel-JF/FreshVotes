package com.freshvotes.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.FeatureService;

import jakarta.servlet.http.HttpServletResponse;


@Controller
public class FeatureController
{
    @Autowired
    private FeatureService featureService;

    @PostMapping("/products/{productId}/features")
    public String createFeature(@AuthenticationPrincipal User user, @PathVariable long productId)
    {
        Feature feature = featureService.createFeature(productId, user);
        return "redirect:/products/" + productId + "/features/" + feature.getId();
    }

    @GetMapping("/products/{productId}/features/{featureId}")
    public String getFeature(@AuthenticationPrincipal User user, ModelMap model, @PathVariable Long featureId, HttpServletResponse response) throws IOException
    {
        featureService.getFeature(user, model, featureId, response);
        return "feature";
    }

    @PostMapping("/products/{productId}/features/{featureId}")
    public String updateFeature(@AuthenticationPrincipal User user, Feature feature, @PathVariable Long productId, @PathVariable Long featureId)
    {
        featureService.updateFeature(user, feature, productId, featureId);
        return "redirect:/products/" + productId + "/features/" + featureId;
    }

    @PostMapping("/products/{productId}/features/{featureId}/status")
    public String setFeatureStatus(@AuthenticationPrincipal User user, @PathVariable Long productId, @PathVariable Long featureId, @RequestParam(value = "status") String status)
    {
        featureService.setFeatureStatus(user, productId, featureId, status);
        return "redirect:/products/" + productId + "/features/" + featureId;
    }

    @PostMapping("p/{productId}/{featureId}/delete")
    public String deleteFeature(@AuthenticationPrincipal User user, @PathVariable Long productId, @PathVariable Long featureId)
    {
        featureService.deleteFeature(user, productId, featureId);
        return "redirect:/p/" + productId;   
    }
}
