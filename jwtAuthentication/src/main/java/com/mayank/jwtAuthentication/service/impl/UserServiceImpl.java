package com.mayank.jwtAuthentication.service.impl;

import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.repository.UserRepository;
import com.mayank.jwtAuthentication.service.EmailService;
import com.mayank.jwtAuthentication.service.OtpService;
import com.mayank.jwtAuthentication.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OtpService otpService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, OtpService otpService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.otpService = otpService;
    }


    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setVerificationCode(otpService.generateOtp());
        user.setVerificationExpiresAt(LocalDateTime.now().plusMinutes(10));
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Override
    public boolean isExistsByUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @Override
    public boolean isExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getAllUser() {
        Iterable<User> userIterable=userRepository.findAll();
        List<User> userList=new ArrayList<>();
        for (User v:userIterable){
            userList.add(v);
        }
        return userList;
    }
}
