package com.freshvotes.web;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

import jakarta.servlet.http.HttpServletResponse;


@Controller
public class ProductController
{
    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/products")
    public String getProducts(ModelMap model)
    {
        return "product";
    }
    
    @GetMapping("/products/{productId}")
    public String getProduct(@PathVariable Long productId, ModelMap model, HttpServletResponse response) throws IOException
    {
        Optional<Product> productOpt = productRepo.findById(productId);

        if(productOpt.isPresent())
        {
            Product product = productOpt.get();
            model.put("product", product);
        }
        else
        {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id " + productId + " was not found.");
        }
        return "product";
    }
    
    @PostMapping("/products")
    public String createProduct(@AuthenticationPrincipal User user)
    {
        Product product = new Product();
        product.setPublished(false);
        product.setUser(user);

        product = productRepo.save(product);

        return "redirect:/products/" + product.getId();
    }

    @PostMapping("/products/{productId}")
    public String saveProduct(@PathVariable Long productId, Product product)
    {
        product = productRepo.save(product);
        return "redirect:/products/" + product.getId();
    }
    
    
}
