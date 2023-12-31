package com.freshvotes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.repositories.FeatureRepository;
import com.freshvotes.repositories.ProductRepository;

@Service
public class FeatureService
{
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private FeatureRepository featureRepo;

    public Feature createFeature(Long productId)
    {
        Feature feature = new Feature();

        Optional<Product> productOpt = productRepo.findById(productId);

        if(productOpt.isPresent())
        {
            Product product = productOpt.get();
            feature.setProduct(product);
            product.getFeatures().add(feature);

            feature.setStatus("Pending review");

            return featureRepo.save(feature);
        }
        return feature;
    }
}
