package ru.nsu.ccfit.petrov.dailyhelperapi.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokens {

    private String accessToken;

    private String refreshToken;
}
