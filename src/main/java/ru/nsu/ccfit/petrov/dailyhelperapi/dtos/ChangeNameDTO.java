package ru.nsu.ccfit.petrov.dailyhelperapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNameDTO {

    @NotEmpty(message = "First name cannot be empty")
    @Size(max = 256, message = "First name length must be less than or equal to 256 symbols")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(max = 256, message = "Last name length must be less than or equal to 256 than symbols")
    private String lastName;
}
