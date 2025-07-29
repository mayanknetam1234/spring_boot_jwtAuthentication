package com.mayank.jwtAuthentication.service;

import com.mayank.jwtAuthentication.entity.User;

public interface AuthenticationService {
    boolean isAuthentic(User user);
}
