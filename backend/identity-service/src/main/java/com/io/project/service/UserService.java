package com.io.project.service;

import java.util.List;

import com.io.project.domain.entity.User;
import com.io.project.rest.dto.RegisterRequestDTO;
import com.io.project.rest.dto.PasswordChangeDTO;

public interface UserService {
    
    User findById(Long id);
    List<User> findByNameLike(String name);
    User findByEmail(String email);
    User findByCPF(String cpf);
    List<User> findAll();
    User save(RegisterRequestDTO dto);
    void delete(Long id);
    void changePassword(Long id, PasswordChangeDTO dto);

}
