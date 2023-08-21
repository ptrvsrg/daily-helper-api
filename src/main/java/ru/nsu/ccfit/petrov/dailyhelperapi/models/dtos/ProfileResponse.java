package ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    @NotNull
    private Long id;

    @NotBlank(message = "First name cannot be null or whitespace")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or whitespace")
    private String lastName;

    @NotBlank(message = "Email cannot be null or whitespace")
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
           message = "Email has invalid format")
    private String email;
}
