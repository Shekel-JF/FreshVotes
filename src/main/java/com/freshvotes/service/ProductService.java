package com.freshvotes.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepo;

    public void getProduct(@AuthenticationPrincipal User user, @PathVariable Long productId, ModelMap model, HttpServletResponse response) throws IOException
    {
        Optional<Product> productOpt = findById(productId);

        if(productOpt.isPresent())
        {
            Product product = productOpt.get();
            if(checkProductOwnership(user, product))
            {
                model.put("product", product);
            }        
        }
        else
        {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id " + productId + " was not found.");
        }
    }

    public Product createProduct(@AuthenticationPrincipal User user)
    {
        Product product = new Product();
        product.setPublished(false);
        product.setUser(user);

        product = save(product);
        return product;
    }

    public void saveProduct(@AuthenticationPrincipal User user, @PathVariable Long productId, Product product)
    {
        if(checkProductOwnership(user, product))
        {
            product = save(product);
        }        
    }

    public void deleteProduct(@AuthenticationPrincipal User user, @RequestParam Long productId)
    {
        Optional<Product> productOpt = findById(productId);
        if(productOpt.isPresent())
        {
            Product product = productOpt.get();
            if(checkProductOwnership(user, product))
            {
                deleteById(productId);
            }   
        }
    }

    public void getProductUserView(@AuthenticationPrincipal User user, @PathVariable Long productId, ModelMap model) throws UnsupportedEncodingException
    {
        if(productId != null)
        {
            Optional<Product> productOpt = findById(productId);
            if(productOpt.isPresent())
            {
                Product product = productOpt.get();
                if(checkProductOwnership(user, product) || product.getPublished())
                {
                    model.put("product", product);
                    model.put("user", user);
                }        
            }
        }
    }

    public Product save(Product product)
    {
        return productRepo.save(product);
    }

        public Optional<Product> findById(Long productId)
    {
        return productRepo.findById(productId);
    }

    public void deleteById(Long ProductId)
    {
        productRepo.deleteById(ProductId);
    }

    public Boolean checkProductOwnership(@AuthenticationPrincipal User user, Product product)
    {
        return product.getUser().getId() == user.getId();
    }  
}
