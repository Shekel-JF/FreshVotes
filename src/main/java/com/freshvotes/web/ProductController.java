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

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;


@Controller
public class ProductController
{
    @Autowired
    private ProductService productService;
    
    @GetMapping("/products/{productId}")
    public String getProduct(@AuthenticationPrincipal User user, @PathVariable Long productId, ModelMap model, HttpServletResponse response) throws IOException
    {
        productService.getProduct(user, productId, model, response);
        return "product";
    }
    
    @PostMapping("/products")
    public String createProduct(@AuthenticationPrincipal User user)
    {
        Product product = productService.createProduct(user);
        return "redirect:/products/" + product.getId();
    }

    @PostMapping("/products/{productId}")
    public String saveProduct(@AuthenticationPrincipal User user, @PathVariable Long productId, Product product)
    {
        productService.saveProduct(user, productId, product);
        return "redirect:/products/" + productId;
    }

    @PostMapping("/products/delete")
    public String deleteProduct(@AuthenticationPrincipal User user, @RequestParam Long productId)
    {
        productService.deleteProduct(user, productId);
        return "redirect:/dashboard";
    }
    
    @GetMapping("p/{productId}")
    public String getProductUserView(@AuthenticationPrincipal User user, @PathVariable Long productId, ModelMap model, HttpServletResponse response) throws IOException
    {
        productService.getProductUserView(user, productId, model, response);
        return "productUserView";  
    } 
}
