package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @NotNull
    private String message;

    @NotNull
    private String debugMessage;

    @JsonInclude(Include.NON_NULL)
    private List<String> errors;

    public ErrorResponse(String message) {
        this(message, message, null);
    }

    public ErrorResponse(String message, String debugMessage) {
        this(message, debugMessage, null);
    }
}
