package dev.pdanh.hello_spring.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    String username;
    String password;
    String lastName;
    String firstName;
    String phoneNumber;
    LocalDate dob;
}
