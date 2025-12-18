package com.shakil1473.dailydairy.controller;

import com.shakil1473.dailydairy.domain.dto.AuthResponse;
import com.shakil1473.dailydairy.domain.dto.LoginRequestDto;
import com.shakil1473.dailydairy.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        UserDetails userDetails = authenticationService.authenticate(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );

        String tokenValue = authenticationService.generateToken(userDetails);
        AuthResponse authResponse =  AuthResponse.builder()
                .token(tokenValue)
                .expiresIn(3600)
                .build();

        return ResponseEntity.ok(authResponse);
    }

}
