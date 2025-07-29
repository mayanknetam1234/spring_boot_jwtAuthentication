package com.mayank.jwtAuthentication.service;

import com.mayank.jwtAuthentication.dto.VerifyUserRequestDto;
import com.mayank.jwtAuthentication.entity.User;

public interface AuthenticationService {
    boolean isAuthentic(User user);
    void verifyOtp(VerifyUserRequestDto verifyUserRequestDto)throws RuntimeException;
}
