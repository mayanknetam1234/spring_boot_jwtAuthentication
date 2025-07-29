package com.mayank.jwtAuthentication.controller;

import com.mayank.jwtAuthentication.dto.UserDto;
import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.mapper.Mapper;
import com.mayank.jwtAuthentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final Mapper<User,UserDto> userDtoModelMapper;

    public UserController(UserService userService, Mapper<User, UserDto> userDtoModelMapper) {
        this.userService = userService;
        this.userDtoModelMapper = userDtoModelMapper;
    }

    @GetMapping("/v1/users")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<User> userList=userService.getAllUser();
        List<UserDto> userDtoList=new ArrayList<>();
        for (User v:userList){
            userDtoList.add(userDtoModelMapper.convertToDto(v));
        }
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
}
