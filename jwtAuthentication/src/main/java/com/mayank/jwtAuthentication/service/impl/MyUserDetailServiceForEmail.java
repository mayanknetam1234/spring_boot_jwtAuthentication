package com.mayank.jwtAuthentication.service.impl;

import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.entity.UserPrincipal;
import com.mayank.jwtAuthentication.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailServiceForEmail implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailServiceForEmail(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByEmail(email);
       if (user.isEmpty()){
           throw new UsernameNotFoundException("User not found");
       }
       return new UserPrincipal(user.get());
    }
}
