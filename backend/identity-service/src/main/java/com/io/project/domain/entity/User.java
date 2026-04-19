package com.io.project.domain.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.io.project.domain.enums.UserRole;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "table_user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", length = 50)
    @NotEmpty(message = "{name.not-empty}")
    private String name;

    @Column(name = "email", length = 50, unique = true)
    @NotEmpty(message = "{email.not-empty}")
    private String email;

    @Column(name = "cpf", length = 50, unique = true)
    @NotEmpty(message = "{cpf.not-empty}")
    private String cpf;

    @Column(name = "password")
    @NotEmpty(message = "{password.not-empty}")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private UserRole role;

}
