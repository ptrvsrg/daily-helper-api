package ru.nsu.ccfit.petrov.dailyhelper.services;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.JwtTokens;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.User;

public interface JwtTokenService {

    JwtTokens createTokens(User user);

    String resolveAccessToken(HttpServletRequest request);

    String getEmailFromAccessToken(String accessToken);

    Date getExpirationFromAccessToken(String accessToken);

    JwtTokens refreshTokens(String refreshToken);

    void deletedExpiredRefreshTokens();
}
