package ru.nsu.ccfit.petrov.dailyhelperapi.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotEmpty(message = "First name cannot be empty")
    @Size(max = 256, message = "First name length cannot be more than 256 symbols")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(max = 256, message = "Last name length cannot be more than 256 symbols")
    private String lastName;

    @NotEmpty(message = "Email cannot be empty")
    @Size(max = 256, message = "Email length cannot be more than 256 symbols")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
           message = "Email has invalid format")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(max = 256, message = "Password length must be less than or equal to 256 symbols")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$",
            message = "Password must contain numbers and letters and not contain whitespaces. " +
                    "Password length must be more than or equal to 8 symbols")
    private String password;
}
