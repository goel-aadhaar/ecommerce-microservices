package com.ecommerce.auth_service.security;

import com.ecommerce.auth_service.entities.Role;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import com.ecommerce.auth_service.entities.User;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Service
public class JwtService {

    private final SecretKey key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;

    public JwtService(
            SecretKey key,
            long accessTtlSeconds,
            long refreshTtlSeconds,
            String issuer
    ) {
        this.key = key;
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issuer = issuer;
    }

    public String generateAccessToken(User user) {

        Instant now = Instant.now();
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .claims(Map.of(
                                "email", user.getEmail(),
                                "roles", roles,
                                "typ", "access"
                         ))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(User user , String jti) {
        Instant now = Instant.now();

        return Jwts.builder()
                .id(jti)
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .claims(Map.of(
                        "email", user.getEmail(),
                        "typ", "refresh"
                ))
                .signWith(key)
                .compact();
    }



}
