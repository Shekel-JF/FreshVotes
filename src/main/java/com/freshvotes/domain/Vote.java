package com.freshvotes.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
public class Vote
{
    private VoteId pk;
    private Boolean upvote;

    @EmbeddedId
    public VoteId getPk()
    {
        return this.pk;
    }
    public void setPk(VoteId pk)
    {
        this.pk = pk;
    }

    public Boolean isUpvote()
    {
        return this.upvote;
    }

    @Transient
    public Boolean getUpvote()
    {
        return this.upvote;
    }

    public void setUpvote(Boolean upvote)
    {
        this.upvote = upvote;
    }
}
