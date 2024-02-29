package com.freshvotes.domain;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
@JsonIdentityInfo(generator=IntSequenceGenerator.class, property="@id")
public class Comment
{
    private Long id;
    private String text;
    private User user;
    private Feature feature;
    private Set<Comment> comments = new TreeSet<>();
    private Comment comment;
    private Date createDate;

    @Column(length=2048)
    public String getText()
    {
        return this.text;
    }
    public void setText(String text)
    {
        this.text = text;
    }

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId()
    {
        return this.id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    @ManyToOne
    @JsonIgnore
    public User getUser()
    {
        return this.user;
    }
    public void setUser(User user)
    {
        this.user = user;
    }

    @ManyToOne
    @JsonIgnore
    public Feature getFeature()
    {
        return this.feature;
    }
    public void setFeature(Feature feature)
    {
        this.feature = feature;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="comment")
    @OrderBy("createDate, id")
    public Set<Comment> getComments()
    {
        return this.comments;
    }
    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }

    @ManyToOne
    @JoinColumn(name="comment_id", nullable=true)
    public Comment getComment()
    {
        return this.comment;
    }
    public void setComment(Comment comment)
    {
        this.comment = comment;
    }

    public Date getCreateDate()
    {
        return this.createDate;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(obj == null)
        {
            return false;
        }
        if(getClass() != obj.getClass())
        {
            return false;
        }
        Comment other = (Comment) obj;
        if(id == null)
        {
            if(other.id != null)
            {
                return false;
            }
            else if(!id.equals(other.id))
            {
                return false;
            }
        }
        return true;
    }
}


