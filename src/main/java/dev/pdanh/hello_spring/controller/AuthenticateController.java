package dev.pdanh.hello_spring.controller;

import dev.pdanh.hello_spring.dto.request.AuthenticateRequest;
import dev.pdanh.hello_spring.dto.response.APIResponse;
import dev.pdanh.hello_spring.dto.response.AuthenticateResponse;
import dev.pdanh.hello_spring.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/identity/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticateController {
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    public APIResponse<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest) {
        boolean authenticated = authenticationService.authenticate(authenticateRequest);
        return APIResponse.<AuthenticateResponse>builder()
                .code(200)
                .data(AuthenticateResponse.builder()
                        .authenticated(authenticated)
                        .build())
                .build();
    }
}
