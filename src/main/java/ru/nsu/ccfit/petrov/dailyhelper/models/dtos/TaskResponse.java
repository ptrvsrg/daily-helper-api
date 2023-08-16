package ru.nsu.ccfit.petrov.dailyhelper.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
