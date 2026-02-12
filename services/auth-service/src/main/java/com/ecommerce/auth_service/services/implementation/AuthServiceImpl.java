package com.ecommerce.auth_service.services.implementation;

import com.ecommerce.auth_service.dtos.LoginRequest;
import com.ecommerce.auth_service.dtos.TokenResponse;
import com.ecommerce.auth_service.dtos.UserDto;
import com.ecommerce.auth_service.services.interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;

public class AuthServiceImpl implements AuthService {

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public void logout(HttpServletRequest request) {

    }

    @Override
    public UserDto register(UserDto userDto) {
        return null;
    }
}
