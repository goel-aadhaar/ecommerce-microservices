package com.ecommerce.auth_service.controllers;

import com.ecommerce.auth_service.dtos.*;
import com.ecommerce.auth_service.services.interfaces.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
            HttpServletResponse response
    ) {

        authService.logout(response);

//        response.set
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
