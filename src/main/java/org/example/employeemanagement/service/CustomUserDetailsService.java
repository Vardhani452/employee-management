package org.example.employeemanagement.service;

import org.example.employeemanagement.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.example.employeemanagement.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(
            UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        System.out.println("Inside loadUserByUsername: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found"));

        System.out.println("User found in DB");

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}