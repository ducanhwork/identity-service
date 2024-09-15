package dev.pdanh.hello_spring.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String id;
    String username;
    String lastName;
    String firstName;
    String phoneNumber;
    LocalDate dob;
    Set<String> roles;
}
