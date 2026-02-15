package com.ecommerce.auth_service.services.implementation;

import com.ecommerce.auth_service.dtos.*;
import com.ecommerce.auth_service.entities.RefreshToken;
import com.ecommerce.auth_service.repositories.RefreshTokenRepository;
import com.ecommerce.auth_service.repositories.UserRepository;
import com.ecommerce.auth_service.security.JwtService;
import com.ecommerce.auth_service.services.interfaces.AuthService;
import com.ecommerce.auth_service.entities.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public TokenResponse login(LoginRequest loginRequest , HttpServletResponse response) {

        Authentication authentication = authenticate(loginRequest);
        User user = (User) authentication.getPrincipal();

        if(!user.isEnabled()) {
            throw new RuntimeException("User account is disabled. Please contact support.");
        }

        String jti = UUID.randomUUID().toString();

        RefreshToken refreshTokenObject = RefreshToken.builder()
                .jti(jti)
                .user(user)
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(jwtService.getRefreshTtlSeconds()))
                .build();

        refreshTokenRepository.save(refreshTokenObject);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user , refreshTokenObject.getJti());

        
    }

    @Override
    public void logout(HttpServletResponse response) {
//        Cookie cookie = new Cookie("refreshToken", null);
    }

    @Override
    public UserResponse register(UserRegistrationRequest userRegistrationRequest) {
        String email = userRegistrationRequest.email();

        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with email " + email + " already exists");
        }

        User newUser = User.builder()
                .firstName(userRegistrationRequest.firstName())
                .lastName(userRegistrationRequest.lastName())
                .email(email)
                .password(passwordEncoder.encode(userRegistrationRequest.password()))
                .build();

        User createdUser = userRepository.save(newUser);

        return UserResponse.fromEntity(createdUser);
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.password()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }
    }

}
