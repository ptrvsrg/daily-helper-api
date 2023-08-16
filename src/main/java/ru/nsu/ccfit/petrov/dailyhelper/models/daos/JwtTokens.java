package ru.nsu.ccfit.petrov.dailyhelper.models.daos;

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
