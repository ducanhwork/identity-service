package dev.pdanh.hello_spring.service;

import dev.pdanh.hello_spring.dto.request.UserCreateRequest;
import dev.pdanh.hello_spring.dto.request.UserUpdateRequest;
import dev.pdanh.hello_spring.dto.response.APIResponse;
import dev.pdanh.hello_spring.dto.response.UserResponse;
import dev.pdanh.hello_spring.entity.User;
import dev.pdanh.hello_spring.exception.AppException;
import dev.pdanh.hello_spring.exception.ErrorCode;
import dev.pdanh.hello_spring.mapper.UserMapper;
import dev.pdanh.hello_spring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createRequest(UserCreateRequest request) {
        User user = new User();
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(userMapper.userToUserResponse(user));
        }
        return userResponses;
    }

    public UserResponse getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.userToUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.userUpdateToUser(request,user);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {

        userRepository.deleteById(id);
    }
}
