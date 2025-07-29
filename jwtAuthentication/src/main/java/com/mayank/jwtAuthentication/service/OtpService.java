package com.mayank.jwtAuthentication.service;

import com.mayank.jwtAuthentication.entity.User;

public interface OtpService {
    String generateOtp();
    void sendOtp(String email,String otp)throws RuntimeException;
    void reSendOtp(String email) throws RuntimeException;
}
