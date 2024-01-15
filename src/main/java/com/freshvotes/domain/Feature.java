package com.freshvotes.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Feature
{
    private Long id;
    private String title;
    private String description;
    private String status;
    private Product product;
    private User user;
    private Set<Comment> comments = new HashSet<>();

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

    @ManyToOne
    public User getUser()
    {
        return user;
    }
    public void setUser(User user)
    {
        this.user = user;
    } 

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="feature")
    public Set<Comment> getComments()
    {
        return comments;
    }
    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }


}
