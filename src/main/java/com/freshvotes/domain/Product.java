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
public class Product
{
    private Long id;
    private String name;
    private User user;
    private Set<Feature> features= new HashSet<>();
    private Boolean published;

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    @ManyToOne
    public User getUser()
    {
        return this.user;
    }
    public void setUser(User user)
    {
        this.user = user;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="product")
    public Set<Feature> getFeatures()
    {
        return this.features;
    }
    public void setFeatures(Set<Feature> features)
    {
        this.features = features;
    }

    public Boolean getPublished()
    {
        return this.published;
    }

    public void setPublished(Boolean published)
    {
        this.published = published;
    }
}
