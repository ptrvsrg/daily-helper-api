package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.LoginRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.LoginResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.RefreshTokensResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.RegisterRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.RegisterResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ResetPasswordRequest;

public interface AuthService {

    RegisterResponse register(@Valid RegisterRequest registerRequest);

    void verify(String activationToken);

    LoginResponse login(@Valid LoginRequest loginRequest);

    RefreshTokensResponse refreshTokens(String refreshToken);

    RegisterResponse resetPassword(@Valid ResetPasswordRequest resetPasswordRequest);
}
