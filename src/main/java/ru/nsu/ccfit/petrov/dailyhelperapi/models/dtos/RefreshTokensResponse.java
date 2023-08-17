package ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokensResponse {

    @NotBlank(message = "Access token cannot be null or whitespace")
    private String accessToken;

    @NotBlank(message = "Refresh token cannot be null or whitespace")
    private String refreshToken;

}
