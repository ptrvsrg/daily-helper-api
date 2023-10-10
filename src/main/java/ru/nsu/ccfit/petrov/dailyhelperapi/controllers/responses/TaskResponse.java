package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    @NotNull
    private Long id;

    @NotBlank(message = "Task name cannot be null or whitespace")
    private String name;

    @JsonInclude(Include.NON_NULL)
    private String description;
}
