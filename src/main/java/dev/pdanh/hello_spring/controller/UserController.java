package dev.pdanh.hello_spring.controller;

import dev.pdanh.hello_spring.dto.request.UserCreateRequest;
import dev.pdanh.hello_spring.dto.request.UserUpdateRequest;
import dev.pdanh.hello_spring.dto.response.APIResponse;
import dev.pdanh.hello_spring.dto.response.UserResponse;
import dev.pdanh.hello_spring.entity.User;
import dev.pdanh.hello_spring.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/identity/users")
public class UserController {
    UserService userService;

    @PostMapping()
    public APIResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest user) {
        return APIResponse.<UserResponse>builder()
                .code(200)
                .data(userService.createRequest(user))
                .build();
    }

    @GetMapping
    public APIResponse<List<UserResponse>> getUsers() {
        return APIResponse.<List<UserResponse>>builder()
                .code(200)
                .data(userService.getUsers())
                .build();
    }
    @GetMapping("/{userId}")
    public APIResponse<UserResponse> getUser(@PathVariable String userId) {
        return APIResponse.<UserResponse>builder()
                .code(200)
                .data(userService.getUser(userId))
                .build();
    }
    @PutMapping("/{userId}")
    public APIResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateRequest user) {
        return APIResponse.<UserResponse>builder()
                .code(200)
                .data(userService.updateUser(userId,user))
                .build();
    }
    @DeleteMapping("/{userId}")
    public APIResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return APIResponse.<String>builder()
                .code(200)
                .message("Delete successfully")
                .build();
    }

}
