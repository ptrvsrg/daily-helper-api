package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Password cannot be null or whitespace")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$",
             message = "Password is insecure")
    private String oldPassword;

    @NotBlank(message = "Password cannot be null or whitespace")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$",
             message = "Password is insecure")
    private String newPassword;
}
