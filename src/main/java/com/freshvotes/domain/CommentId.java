package com.freshvotes.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
public class CommentId implements Serializable
{
    private static final long serialVersionUID = -4011394447L;
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
