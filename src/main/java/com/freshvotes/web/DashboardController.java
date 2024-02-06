package com.freshvotes.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.freshvotes.domain.Product;
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

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, ModelMap model)
    {
        model.put("userProducts", productService.findByUser(user));

        List<Product> popularProducts = productService.findByUpvotes();
        List<List<Product>> productLists = new ArrayList<>();
        for(int i = 0; i < popularProducts.size(); i+=2)
        {
            {
                if(i + 1 < popularProducts.size())
                {
                    productLists.add(Arrays.asList(popularProducts.get(i), popularProducts.get(i+1)));
                }
                else
                {
                    productLists.add(Arrays.asList(popularProducts.get(i)));
                }
                
            }
        }
        
        model.put("popularProducts", productLists);
        System.out.println(productLists);
        
        return "dashboard";
    }
}
