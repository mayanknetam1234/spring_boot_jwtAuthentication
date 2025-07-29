package com.mayank.jwtAuthentication.mapper.impl;

import com.mayank.jwtAuthentication.dto.UserDto;
import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class UserDtoMapper implements Mapper<User, UserDto> {
    private final ModelMapper modelMapper;

    public UserDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User convertToEntity(UserDto a) {
        return modelMapper.map(a, User.class);
    }

    @Override
    public UserDto convertToDto(User a) {
        return modelMapper.map(a, UserDto.class);
    }
}
