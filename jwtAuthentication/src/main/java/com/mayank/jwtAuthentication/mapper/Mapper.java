package com.mayank.jwtAuthentication.mapper;

public interface Mapper <X,Y>{
    X convertToEntity(Y a);
    Y convertToDto(X a);
}
