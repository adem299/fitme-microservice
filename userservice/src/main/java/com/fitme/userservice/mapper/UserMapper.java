package com.fitme.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.fitme.userservice.dto.RegisterRequest;
import com.fitme.userservice.dto.UserResponse;
import com.fitme.userservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(RegisterRequest request);
}
