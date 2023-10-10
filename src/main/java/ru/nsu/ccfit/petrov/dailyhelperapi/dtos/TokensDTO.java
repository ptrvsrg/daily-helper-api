package ru.nsu.ccfit.petrov.dailyhelperapi.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokensDTO {

    @NotEmpty(message = "Access token cannot be empty")
    private String accessToken;

    @NotEmpty(message = "Refresh token cannot be empty")
    private String refreshToken;
}
