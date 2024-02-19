package com.freshvotes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.freshvotes.domain.User;
import com.freshvotes.service.ProductService;

@Controller
public class DashboardController
{
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String rootView ()
    {
        return "index";
    }

    @GetMapping("/discover")
    public String discover(@AuthenticationPrincipal User user, ModelMap model)
    {
        model.put("user", user);
        productService.putGroupedLists(model, productService.findByUpvotes(), "popularProducts");  
        productService.putGroupedLists(model, productService.findNew(), "newProducts");

        return "discover";
    }
  
    @GetMapping("/discover/search/")
    public String productSearch(@AuthenticationPrincipal User user, ModelMap model)
    {
        model.put("searchedProducts", productService.findAllPublic());

        return "productSearch";
    }

    @GetMapping("/discover/search/{typedProductName}")
    public String productSearch(@AuthenticationPrincipal User user, ModelMap model, @PathVariable String typedProductName)
    {
        model.put("searchedProducts", productService.findByKeyWord(typedProductName));
        
        return "productSearch";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, ModelMap model)
    {
        model.put("userProducts", productService.findByUser(user));
        model.put("user", user);

        return "dashboard";
    }
}
