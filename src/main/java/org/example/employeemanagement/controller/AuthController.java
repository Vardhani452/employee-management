package org.example.employeemanagement.controller;

import org.example.employeemanagement.dto.LoginRequest;
import org.example.employeemanagement.dto.LoginResponse;
import org.example.employeemanagement.entity.User;
import org.example.employeemanagement.repository.UserRepository;
import org.example.employeemanagement.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        System.out.println("Step 1 - Login API entered");

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()));

        System.out.println("Step 2 - Authentication successful");

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        System.out.println("Step 3 - UserDetails loaded");

        String token = jwtUtil.generateToken(userDetails);

        System.out.println("Step 4 - JWT generated");

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }

        userRepository.save(user);

        return ResponseEntity.ok("User Registered Successfully");
    }
}
