package ru.nsu.ccfit.petrov.dailyhelperapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {

    private String oldPassword;

    @NotEmpty(message = "Password cannot be empty")
    @Size(max = 256, message = "Password length must be less than or equal to 256 symbols")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$",
            message = "Password must contain numbers and letters and not contain whitespaces. " +
                    "Password length must be more than or equal to 8 symbols")
    private String newPassword;
}
