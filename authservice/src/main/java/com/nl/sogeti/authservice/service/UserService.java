package com.nl.sogeti.authservice.service;

import com.nl.sogeti.authservice.config.JWTTokenProvider;
import com.nl.sogeti.authservice.entity.User;
import com.nl.sogeti.authservice.exceptions.UserAlreadyExists;
import com.nl.sogeti.authservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository _repository;

    private BCryptPasswordEncoder _passwordEncoder;

    private JWTTokenProvider _token;

    public String signUp(User user) {
        this._repository.findByUsername(user.getUsername())
                .orElseThrow(UserAlreadyExists::new);

        user.setPassword(_passwordEncoder.encode(user.getPassword()));

        this._repository.save(user);

        return "User is created successfully";
    }

    public String signIn(User user) {
        var existingUser = this._repository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user or password!"));

        if (!_passwordEncoder.matches(user.getPassword(), existingUser.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }

        return this._token.generateToken(user.getUsername());
    }

}