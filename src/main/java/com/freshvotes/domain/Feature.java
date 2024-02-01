package com.freshvotes.domain;

import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
@JsonIdentityInfo(generator=IntSequenceGenerator.class, property="@id")
public class Feature
{
    private Long id;
    private String title;
    private String description;
    private String status;
    private Product product;
    private User user;
    private Set<Comment> comments = new TreeSet<>();

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

    @Column(length = 1024)
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
    @OrderBy("createDate, id")
    public Set<Comment> getComments()
    {
        return comments;
    }
    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }
}
