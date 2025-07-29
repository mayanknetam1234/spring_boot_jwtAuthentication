package com.mayank.jwtAuthentication.mapper.impl;

import com.mayank.jwtAuthentication.dto.LoginRequestDto;
import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class LoginRequestDtoMapper implements Mapper<User, LoginRequestDto> {

    private final ModelMapper modelMapper;

    public LoginRequestDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public User convertToEntity(LoginRequestDto a) {
        return modelMapper.map(a, User.class);
    }

    @Override
    public LoginRequestDto convertToDto(User a) {
        return modelMapper.map(a,LoginRequestDto.class);
    }
}
