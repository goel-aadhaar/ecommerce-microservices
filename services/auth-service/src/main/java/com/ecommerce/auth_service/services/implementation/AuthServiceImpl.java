package com.ecommerce.auth_service.services.implementation;

import com.ecommerce.auth_service.dtos.*;
import com.ecommerce.auth_service.repositories.UserRepository;
import com.ecommerce.auth_service.services.interfaces.AuthService;
import com.ecommerce.auth_service.entities.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse login(LoginRequest loginRequest , HttpServletResponse response) {
        return null;
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
}
