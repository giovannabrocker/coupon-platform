package com.io.project.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.io.project.domain.entity.User;
import com.io.project.domain.repository.UserRepository;
import com.io.project.exception.ServiceRuleException;
import com.io.project.rest.dto.RegisterRequestDTO;
import com.io.project.rest.dto.PasswordChangeDTO;
import com.io.project.service.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new ServiceRuleException("Id de usuário não encontrado."));
    }

    @Override
    public List<User> findByNameLike(String name) {
        return repository.findByNameLike(name);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
            () -> new ServiceRuleException("E-mail de usuário não encontrado."));
    }

    @Override
    public User findByCPF(String cpf) {
        return repository.findByCPF(cpf).orElseThrow(
            () -> new ServiceRuleException("CPF de usuário não encontrado."));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public User save(RegisterRequestDTO dto) {
        repository.findByEmail(dto.email()).ifPresent(u -> {
            throw new ServiceRuleException("E-mail já cadastrado");
        });
        User user = objectFromDto(dto);
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.findById(id)
            .map(u -> {
                repository.delete(u);
                return u;
            }).orElseThrow(
                () -> new ServiceRuleException("Id de usuário não encontrado"));
    }

    private User objectFromDto(@Valid RegisterRequestDTO dto) {
        return User.builder()
            .name(dto.name())
            .email(dto.email())
            .password(dto.password())
            .build();
    }

    @Override
    @Transactional
    public void changePassword(Long id, PasswordChangeDTO dto) {
        User user = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new ServiceRuleException("Senha atual incorreta.");        
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        repository.save(user);
    }

}
