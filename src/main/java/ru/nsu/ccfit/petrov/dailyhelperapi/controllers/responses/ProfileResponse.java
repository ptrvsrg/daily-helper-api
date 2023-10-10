package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    @NotBlank(message = "First name cannot be null or whitespace")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or whitespace")
    private String lastName;

    @NotBlank(message = "Email cannot be null or whitespace")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
           message = "Email has invalid format")
    private String email;
}
