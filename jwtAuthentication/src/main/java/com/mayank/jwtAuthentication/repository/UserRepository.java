package com.mayank.jwtAuthentication.repository;

import com.mayank.jwtAuthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);


    boolean existsByUsername(String userName);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
