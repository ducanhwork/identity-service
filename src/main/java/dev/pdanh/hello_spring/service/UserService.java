package dev.pdanh.hello_spring.service;

import dev.pdanh.hello_spring.dto.request.UserCreateRequest;
import dev.pdanh.hello_spring.dto.request.UserUpdateRequest;
import dev.pdanh.hello_spring.dto.response.APIResponse;
import dev.pdanh.hello_spring.dto.response.UserResponse;
import dev.pdanh.hello_spring.entity.User;
import dev.pdanh.hello_spring.enums.Role;
import dev.pdanh.hello_spring.exception.AppException;
import dev.pdanh.hello_spring.exception.ErrorCode;
import dev.pdanh.hello_spring.mapper.UserMapper;
import dev.pdanh.hello_spring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createRequest(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')") //check truoc khi goi ham neu co role admin moi cho goi ham
    public List<UserResponse> getUsers() {
        log.info("in method getUsers");
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(userMapper.userToUserResponse(user));
        }
        return userResponses;
    }

    @PostAuthorize("returnObject.username==authentication.name") // neu dung la thang dang dang nhap thi moi lay duoc
    public UserResponse getUser(String id) {
        log.info("in method getUserById");
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.userToUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.userUpdateToUser(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfor() {
        log.info("in method getMyInfo");
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        log.info("name: " + name);
        return userMapper.userToUserResponse(userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
