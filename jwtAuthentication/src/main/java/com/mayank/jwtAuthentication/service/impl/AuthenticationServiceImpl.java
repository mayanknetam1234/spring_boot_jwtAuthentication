package com.mayank.jwtAuthentication.service.impl;

import com.mayank.jwtAuthentication.dto.VerifyUserRequestDto;
import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.repository.UserRepository;
import com.mayank.jwtAuthentication.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    @Override
    public boolean isAuthentic(User user) {
      Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              user.getEmail(),user.getPassword()
      ));
      return authentication.isAuthenticated();
    }

    @Override
    public void verifyOtp(VerifyUserRequestDto verifyUserRequestDto)throws RuntimeException {
        Optional<User> userOptional=userRepository.findByEmail(verifyUserRequestDto.getEmail());
        if(userOptional.isPresent()){
            User user=userOptional.get();
            if(user.getVerificationExpiresAt().isBefore(LocalDateTime.now())){
                throw new RuntimeException("Token Expired");
            }
            if (!user.getVerificationCode().equals(verifyUserRequestDto.getOtp())){
                throw new RuntimeException("Invalid Otp");
            }
            user.setEnabled(true);
            user.setVerificationCode(null);
            user.setVerificationExpiresAt(null);
            userRepository.save(user);
        }
        else {
            throw new RuntimeException("User Not Found");
        }
    }
}
