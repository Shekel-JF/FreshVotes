package com.freshvotes.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Feature
{
    private Long id;
    private String title;
    private String description;
    private String status;
    private Product product;

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return this.title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return this.description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getStatus()
    {
        return this.status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    @ManyToOne
    public Product getProduct()
    {
        return this.product;
    }
    public void setProduct(Product product)
    {
        this.product = product;
    }  
}
