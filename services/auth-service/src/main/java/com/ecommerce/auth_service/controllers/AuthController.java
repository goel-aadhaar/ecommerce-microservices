package com.ecommerce.auth_service.controllers;

import com.ecommerce.auth_service.dtos.*;
import com.ecommerce.auth_service.services.interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        TokenResponse tokenResponse = authService.login(loginRequest, response);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        authService.logout(request , response);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody UserRegistrationRequest userRegistrationRequest
    ) {
//        incomplete - handle already existing email
        UserResponse createdUser = authService.register(userRegistrationRequest);
        return ResponseEntity.ok(createdUser);
    }
}
