package ru.nsu.ccfit.petrov.dailyhelper.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNameRequest {

    @NotBlank(message = "First name cannot be null or whitespace")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or whitespace")
    private String lastName;
}
