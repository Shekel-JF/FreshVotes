package com.freshvotes.domain;



import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
public class VoteId implements Serializable
{
    private static final long serialVersionUID = 943424757L;
    private User user;
    private Feature feature;

    @ManyToOne
    public User getUser()
    {
        return this.user;
    }
    public void setUser(User user)
    {
        this.user = user;
    }

    @ManyToOne
    public Feature getFeature()
    {
        return this.feature;
    }
    public void setFeature(Feature feature)
    {
        this.feature = feature;
    }
}
