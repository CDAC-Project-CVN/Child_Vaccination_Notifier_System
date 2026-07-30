package com.cvn.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cvn.user.dto.request.LoginRequest;
import com.cvn.user.dto.request.RefreshTokenRequest;
import com.cvn.user.dto.request.RegisterClinicRequest;
import com.cvn.user.dto.request.RegisterParentRequest;
import com.cvn.user.dto.response.ApiResponse;
import com.cvn.user.dto.response.AuthResponse;
import com.cvn.user.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/parent")
    public ResponseEntity<AuthResponse> registerParent(
            @Valid @RequestBody RegisterParentRequest request) {

        AuthResponse response = authService.registerParent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/register/clinic")
    public ResponseEntity<AuthResponse> registerClinic(
            @Valid @RequestBody RegisterClinicRequest request) {

        AuthResponse response = authService.registerClinic(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        AuthResponse response = authService.refreshToken(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
            @Valid @RequestBody RefreshTokenRequest request) {

        authService.logout(request);

        return ResponseEntity.ok(
        	    ApiResponse.builder()
        	            .success(true)
        	            .message("Logout successful.")
        	            .build()
        	);
    }

}