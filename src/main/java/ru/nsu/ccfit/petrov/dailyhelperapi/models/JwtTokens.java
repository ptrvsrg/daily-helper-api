package ru.nsu.ccfit.petrov.dailyhelperapi.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokens {

    private String accessToken;

    private String refreshToken;
}
