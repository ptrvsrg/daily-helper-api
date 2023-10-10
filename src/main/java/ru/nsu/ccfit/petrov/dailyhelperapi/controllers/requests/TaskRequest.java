package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Task name cannot be null or whitespace")
    private String name;

    private String description;
}
