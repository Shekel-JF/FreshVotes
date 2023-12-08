package com.freshvotes.security;

import org.springframework.security.core.GrantedAuthority;

import com.freshvotes.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Authority implements GrantedAuthority
{
    private static final long serialVersionUID = -6420181486L;
    private Long id;
    private String authority;
    private User user;
    @Override
    public String getAuthority()
    {
        return this.authority;
    }
    public void setAuthority(String authority)
    {
        this.authority = authority;
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

    @ManyToOne()
    public User getUser()
    {
        return this.user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

}