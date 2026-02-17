package com.ecommerce.auth_service.dtos;

public record TokenResponse(
        String accessToken,
        long expiresIn,
        String tokenType,
        UserResponse user
) {
    public static TokenResponse of(String accessToken, long expiresIn, UserResponse user) {
        return new TokenResponse(accessToken, expiresIn, "Bearer", user);
    }
}
