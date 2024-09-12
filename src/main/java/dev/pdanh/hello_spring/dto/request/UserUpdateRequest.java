package dev.pdanh.hello_spring.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.pdanh.hello_spring.util.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 3,message = "INVALID_USERNAME")
    String username;
    @Size(min = 8, message = "INVALID_LENGTH_PASSWORD")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "INVALID_PASSWORD")
    String password;
    @NotBlank(message = "Not blank")
    String lastName;
    @NotBlank(message = "Not blank")
    String firstName;
    @PhoneNumber
    String phoneNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dob;
}
