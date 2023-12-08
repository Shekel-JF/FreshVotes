package com.freshvotes.service;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceTest
{
    @Test
    public void generate_encrypted_password()
    {
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        String raw = "password";
        String encodedPassword = encoder.encode(raw);
        
        System.out.println(encodedPassword);
        assertThat(raw, not(encodedPassword));
        
    }
}
