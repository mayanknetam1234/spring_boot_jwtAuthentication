package com.mayank.jwtAuthentication.service.impl;

import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.entity.UserPrincipal;
import com.mayank.jwtAuthentication.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailServiceForEmail implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailServiceForEmail(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email);
        return new UserPrincipal(user);
    }
}
