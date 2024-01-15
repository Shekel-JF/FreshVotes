package com.freshvotes.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Comment
{
    private Long id;
    private String text;
    private User user;
    private Feature feature;
    private List<Comment> comments = new ArrayList<>();
    private Comment comment;
    private Date createDate;

    @Column(length=5000)
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

    @OneToMany(mappedBy="comment")
    public List<Comment> getComments()
    {
        return this.comments;
    }
    public void setComments(List<Comment> comments)
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

}


