package com.bisw.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Implement your logic to fetch user from the database
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Returning hardcoded user for demonstration
        return org.springframework.security.core.userdetails.User
                .withUsername("admin")
                .password("{noop}password") // {noop} for plain text password, use a PasswordEncoder for hashed passwords
                .roles("ADMIN")
                .build();
    }
}
