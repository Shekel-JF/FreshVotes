package com.freshvotes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepo;

    public void getProduct(User user, Long productId, ModelMap model, HttpServletResponse response) throws IOException
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
            response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id = " + productId + " was not found.");
        }
    }

    public Product createProduct(User user)
    {
        Product product = new Product();
        product.setPublished(false);
        product.setUser(user);

        product = save(product);
        return product;
    }

    public void saveProduct(User user, Long productId, Product product)
    {
        if(checkProductOwnership(user, product))
        {
            product = save(product);
        }        
    }

    public void deleteProduct(User user, Long productId)
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

    public void getProductUserView(User user, Long productId, ModelMap model, HttpServletResponse response) throws IOException
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
            else
            {
                response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id = " + productId + " was not found.");
            }
        }
    }

    public void putNewProductLists(ModelMap model)
    {
        List<Product> newProducts = findNew();
        List<List<Product>> newProductLists = new ArrayList<>();
        
        for(int i = 0; i < newProducts.size(); i+=2)
        {
            {
                if(i + 1 < newProducts.size())
                {
                    newProductLists.add(Arrays.asList(newProducts.get(i), newProducts.get(i+1)));
                }
                else
                {
                    newProductLists.add(Arrays.asList(newProducts.get(i)));
                }    
            }
        }
        model.put("newProducts", newProductLists);
    }

    public void putPopularProductLists(ModelMap model)
    {
        List<Product> popularProducts = findByUpvotes();
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
    }

    public Product save(Product product)
    {
        return productRepo.save(product);
    }

    public Optional<Product> findById(Long productId)
    {
        return productRepo.findById(productId);
    }

    public List<Product> findByUser(User user)
    {
        return productRepo.findByUser(user);
    }

    public List<Product> findByUpvotes()
    {
        return productRepo.findByUpvotes();
    }
    public List<Product> findNew()
    {
        return productRepo.findNew();
    }

    public void deleteById(Long ProductId)
    {
        productRepo.deleteById(ProductId);
    }

    public Boolean checkProductOwnership(User user, Product product)
    {
        return product.getUser().getId() == user.getId();
    }  
}
