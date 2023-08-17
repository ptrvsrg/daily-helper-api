package ru.nsu.ccfit.petrov.dailyhelper.services;

import jakarta.validation.Valid;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.LoginRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.LoginResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ResetPasswordRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.RefreshTokensResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.RegisterRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.RegisterResponse;

public interface AuthService {

    RegisterResponse register(@Valid RegisterRequest registerRequest);

    void verify(String activationToken);

    LoginResponse login(@Valid LoginRequest loginRequest);

    RefreshTokensResponse refreshTokens(String refreshToken);

    RegisterResponse resetPassword(@Valid ResetPasswordRequest resetPasswordRequest);
}
