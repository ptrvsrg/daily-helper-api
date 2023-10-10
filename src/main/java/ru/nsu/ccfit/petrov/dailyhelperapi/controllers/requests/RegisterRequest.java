package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First name cannot be null or whitespace")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or whitespace")
    private String lastName;

    @NotBlank(message = "Email cannot be null or whitespace")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
           message = "Email has invalid format")
    private String email;

    @NotBlank(message = "Password cannot be null or whitespace")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$",
             message = "Password is insecure")
    private String password;
}
