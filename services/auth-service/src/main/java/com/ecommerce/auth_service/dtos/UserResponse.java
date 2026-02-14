package com.ecommerce.auth_service.dtos;

import com.ecommerce.auth_service.entities.User;
import com.ecommerce.auth_service.enums.Provider;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record UserResponse(
    UUID id,
    String firstName,
    String lastName,
    String email,
    Set<String> roles,
    Provider provider,
    boolean enable,
    Instant createdAt

) {
    public static UserResponse fromEntity(User user) {
        Set<String> roles = (user.getRoles() != null) ? user
                    .getRoles()
                    .stream()
                    .map(role -> role.getName())
                    .collect(Collectors.toSet())
                : Set.of();

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(roles)
                .provider(user.getProvider())
                .enable(user.isEnable())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
