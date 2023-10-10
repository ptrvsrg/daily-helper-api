package ru.nsu.ccfit.petrov.dailyhelperapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    @NotEmpty(message = "Project name cannot be empty")
    @Size(max = 256, message = "Project name length must be less than or equal to 256 symbols")
    private String name;

    private String description;
}
