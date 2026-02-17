package com.ecommerce.auth_service.services.interfaces;

import com.ecommerce.auth_service.dtos.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    TokenResponse login(LoginRequest loginRequest , HttpServletResponse response);

    void logout(HttpServletRequest request , HttpServletResponse response);

    UserResponse register(UserRegistrationRequest userRegistrationRequest);

}
