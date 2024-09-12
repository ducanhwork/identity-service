package dev.pdanh.hello_spring.mapper;

import dev.pdanh.hello_spring.dto.request.UserCreateRequest;
import dev.pdanh.hello_spring.dto.request.UserUpdateRequest;
import dev.pdanh.hello_spring.dto.response.UserResponse;
import dev.pdanh.hello_spring.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse userToUserResponse(User user);
    User toUser(UserCreateRequest userCreateRequest);
    void userUpdateToUser(UserUpdateRequest userUpdateRequest,@MappingTarget User user);
}
