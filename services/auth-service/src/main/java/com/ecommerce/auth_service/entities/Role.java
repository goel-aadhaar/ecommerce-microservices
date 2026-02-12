package com.ecommerce.auth_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
@Table(name = "roles")
public class Role {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(unique = true , nullable = false)
    private String name;
}
