package com.freshvotes.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;


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

    public Boolean getUpvote()
    {
        return this.upvote;
    }
    public void setUpvote(Boolean upvote)
    {
        this.upvote = upvote;
    }


    @Override
    public String toString() {
        return "{" +
            "upvote='" + getUpvote() + "'" +
            "}";
    }
}
