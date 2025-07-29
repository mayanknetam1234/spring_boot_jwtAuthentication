package com.mayank.jwtAuthentication.mapper.impl;

import com.mayank.jwtAuthentication.dto.RegisterRequestDto;
import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class RegisterRequestDtoMapper implements Mapper<User, RegisterRequestDto> {
    private final ModelMapper modelMapper;

    public RegisterRequestDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User convertToEntity(RegisterRequestDto a) {
        return modelMapper.map(a, User.class);
    }

    @Override
    public RegisterRequestDto convertToDto(User a) {
        return modelMapper.map(a,RegisterRequestDto.class);
    }
}
