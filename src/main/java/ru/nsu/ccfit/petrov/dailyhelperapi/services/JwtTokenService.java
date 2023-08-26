package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.entities.JwtTokens;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.entities.User;

public interface JwtTokenService {

    JwtTokens createTokens(User user);

    String resolveAccessToken(HttpServletRequest request);

    String getEmailFromAccessToken(String accessToken);

    Date getExpirationFromAccessToken(String accessToken);

    JwtTokens refreshTokens(String refreshToken);

    void deletedExpiredRefreshTokens();
}
