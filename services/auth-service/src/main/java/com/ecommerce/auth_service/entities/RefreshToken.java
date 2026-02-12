package com.ecommerce.auth_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "refresh_tokens")
@Table(name = "refresh_tokens" , indexes = {
    @Index(name = "refresh_tokens_jti_idx" , columnList = "jti" , unique = true),
    @Index(name = "refresh_tokens_user_id_idx" , columnList = "user_id")
})
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "jti" , unique = true , nullable = false , updatable = false)
    private String jti;

    @ManyToOne(optional = false , fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false , updatable = false)
    private User user;

    @Column(updatable = false , nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private String expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    private String replacedByToken;
}
