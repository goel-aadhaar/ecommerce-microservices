package com.ecommerce.auth_service.services.interfaces;

import com.ecommerce.auth_service.dtos.LoginRequest;
import com.ecommerce.auth_service.dtos.TokenResponse;
import com.ecommerce.auth_service.dtos.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    TokenResponse login(LoginRequest loginRequest , HttpServletResponse);

    void logout(HttpServletRequest request , HttpServletResponse);

    UserDto register(UserDto userDto);

}
