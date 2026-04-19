package com.io.project.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.io.project.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query(" select u from User u where u.name like :name ")
    List<User> findByNameLike(@Param("name") String name);

    @Query(" select u from User u where u.email=:email ")
    Optional<User> findByEmail(@Param("email") String email);

    @Query(" select u from User u where u.cpf=:cpf ")
    Optional<User> findByCPF(@Param("cpf") String cpf);
}
