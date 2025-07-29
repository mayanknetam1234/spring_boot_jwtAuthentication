package com.mayank.jwtAuthentication.controller;

import com.mayank.jwtAuthentication.dto.AuthResponseDto;
import com.mayank.jwtAuthentication.dto.LoginRequestDto;
import com.mayank.jwtAuthentication.dto.RegisterRequestDto;
import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.mapper.Mapper;
import com.mayank.jwtAuthentication.service.AuthenticationService;
import com.mayank.jwtAuthentication.service.UserService;
import com.mayank.jwtAuthentication.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final Mapper<User,RegisterRequestDto> registerRequestDtoMapper;
    private final Mapper<User,LoginRequestDto> loginRequestDtoMapper;
    private final AuthenticationService authenticationService;

    public AuthController(UserService userService, UserDetailsService userDetailsService, JwtService jwtService, Mapper<User, RegisterRequestDto> registerRequestDtoMapper, Mapper<User, LoginRequestDto> loginRequestDtoMapper, AuthenticationService authenticationService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.registerRequestDtoMapper = registerRequestDtoMapper;
        this.loginRequestDtoMapper = loginRequestDtoMapper;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/v1/auth/register")
    public ResponseEntity<AuthResponseDto> registerUser(@RequestBody RegisterRequestDto registerRequestDto){
        User user=registerRequestDtoMapper.convertToEntity(registerRequestDto);

        if(userService.isExistsByUsername(user.getUsername())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.saveUser(user);
        System.out.println(user.toString());

        AuthResponseDto authResponseDto=AuthResponseDto.builder()
                .token(jwtService.generateToken(userDetailsService.loadUserByUsername(user.getUsername())))
                .build();
        return  new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/v1/auth/login")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto){
        User user=loginRequestDtoMapper.convertToEntity(loginRequestDto);
        System.out.println(user.toString());

        if(!userService.isExistsByUsername(user.getUsername())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!authenticationService.isAuthentic(user)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        AuthResponseDto authResponseDto=AuthResponseDto.builder()
                .token(jwtService.generateToken(userDetailsService.loadUserByUsername(user.getUsername())))
                .build();
        return  new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
    }



}
