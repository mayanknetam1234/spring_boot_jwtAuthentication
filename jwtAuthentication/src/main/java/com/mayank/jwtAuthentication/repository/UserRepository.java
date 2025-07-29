package com.mayank.jwtAuthentication.repository;

import com.mayank.jwtAuthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);


    boolean existsByUsername(String userName);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
