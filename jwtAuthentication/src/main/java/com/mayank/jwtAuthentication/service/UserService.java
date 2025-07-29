package com.mayank.jwtAuthentication.service;

import com.mayank.jwtAuthentication.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    boolean isExistsByUsername(String userName);

    boolean isExistsByEmail(String email);

    List<User> getAllUser();
}
