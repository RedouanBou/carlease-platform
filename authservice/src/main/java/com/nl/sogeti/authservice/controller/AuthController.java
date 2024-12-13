package com.nl.sogeti.authservice.controller;

import com.nl.sogeti.authservice.config.JWTTokenProvider;
import com.nl.sogeti.authservice.dto.AuthResponseDto;
import com.nl.sogeti.authservice.dto.SignInRequestDto;
import com.nl.sogeti.authservice.dto.SignUpRequestDto;
import com.nl.sogeti.authservice.dto.SignUpResponseDto;
import com.nl.sogeti.authservice.entity.User;
import com.nl.sogeti.authservice.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService _service;
    private final ModelMapper _mapper;
    private final JWTTokenProvider _tokenProvider;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        try {
            boolean isValid = _tokenProvider.validateToken(token);
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        var mappedUser = this._mapper.map(signUpRequestDto, User.class);

        var message = this._service.signUp(mappedUser);

        var response = SignUpResponseDto.builder()
                .message(message)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        var mappedUser = this._mapper.map(signInRequestDto, User.class);

        var token = this._service.signIn(mappedUser);

        var response = AuthResponseDto.builder()
                .token(token)
                .message("Sign In Successfull")
                .build();

        return ResponseEntity.ok(response);
    }

}
