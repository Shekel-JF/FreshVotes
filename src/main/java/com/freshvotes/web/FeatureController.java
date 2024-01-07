package com.freshvotes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.service.FeatureService;

@RequestMapping("products/{productId}/features")
public class FeatureController
{
    @Autowired
    private FeatureService featureService;

    @PostMapping("/")
    public String createFeature(@PathVariable long productId)
    {
        featureService.createFeature(productId);
        return "feature";
    }
}
