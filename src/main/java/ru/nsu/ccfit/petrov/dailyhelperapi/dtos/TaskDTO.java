package ru.nsu.ccfit.petrov.dailyhelperapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;

    @NotEmpty(message = "Task name cannot be empty")
    @Size(max = 256, message = "Task name length cannot be more than 256 symbols")
    private String name;

    private String description;
}
