package com.mayank.jwtAuthentication.service.impl;

import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.entity.UserPrincipal;
import com.mayank.jwtAuthentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        return new UserPrincipal(user);
    }
}
