package com.io.project.rest.dto;

import com.io.project.domain.enums.UserRole;

public record RegisterRequestDTO(
    String name,
    String email,
    String password,
    String cpf,
    UserRole role
) {
}
