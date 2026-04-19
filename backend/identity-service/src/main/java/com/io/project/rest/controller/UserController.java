package com.io.project.rest.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;

import com.io.project.domain.entity.User;
import com.io.project.rest.dto.RegisterRequestDTO;
import com.io.project.rest.dto.PasswordChangeDTO;
import com.io.project.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@Tag(name = "Usuário", description = "API para operações relacionadas ao usuário")
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService service;

    @Operation(summary = "Busca usuário por ID")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Busca todos os usuários")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<User> getAllUser() {
        return service.findAll();
    }

    @Operation(summary = "Busca usuário por E-mail")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @Operation(summary = "Busca usuário por CPF")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cpf/{cpf}")
    public User getUserByCPF(@PathVariable String cpf) {
        return service.findByCPF(cpf);
    }

    @Operation(summary = "Registra usuário no banco de dados")
    @PostMapping
    @ResponseStatus(CREATED)
    public User postUser(@RequestBody @Valid RegisterRequestDTO dto) {
        return service.save(dto);
    } 

    @Operation(summary = "Deleta usuário pelo seu ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        service.delete(id);
    }

    @Operation(summary = "Altera senha de autenticação de usuário")
    @PutMapping("/change-password")
    @ResponseStatus(NO_CONTENT)
    public void changePassword(@RequestBody PasswordChangeDTO dto, @AuthenticationPrincipal User user) {
        service.changePassword(user.getId(), dto);
    }

}
