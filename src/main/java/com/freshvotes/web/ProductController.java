package com.freshvotes.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;
import com.freshvotes.repositories.UpvoteRepository;
import com.freshvotes.service.FeatureService;

import jakarta.servlet.http.HttpServletResponse;


@Controller
public class ProductController
{
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private UpvoteRepository upvoteRepo;
    
    @GetMapping("/products/{productId}")
    public String getProduct(@AuthenticationPrincipal User user, @PathVariable Long productId, ModelMap model, HttpServletResponse response) throws IOException
    {
        Optional<Product> productOpt = productRepo.findById(productId);

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
    public String saveProduct(@AuthenticationPrincipal User user, @PathVariable Long productId, Product product)
    {
        if(checkProductOwnership(user, product))
        {
            product = productRepo.save(product);
        }        
        return "redirect:/products/" + productId;
    }

    @PostMapping("/products/delete")
    public String deleteProduct(@AuthenticationPrincipal User user, @RequestParam Long productId)
    {
        Optional<Product> productOpt = productRepo.findById(productId);
        if(productOpt.isPresent())
        {
            Product product = productOpt.get();
            if(checkProductOwnership(user, product))
            {
                productRepo.deleteById(productId);
            }   
        }
        return "redirect:/dashboard";
    }
    
    @GetMapping("p/{productId}")
    public String productUserView(@AuthenticationPrincipal User user, @PathVariable Long productId, ModelMap model) throws UnsupportedEncodingException
    {
        if(productId != null)
        {
            Optional<Product> productOpt = productRepo.findById(productId);
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
        return "productUserView";  
    } 

    public Boolean checkProductOwnership(@AuthenticationPrincipal User user, Product product)
    {
        return product.getUser().getId() == user.getId();
    }  
}
