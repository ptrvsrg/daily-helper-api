package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.AuthDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.TokensDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.UserDTO;

public interface AuthService {

    UserDTO register(@Valid UserDTO userDTO);

    void verify(String activationToken);

    TokensDTO login(@Valid AuthDTO authDTO);

    TokensDTO refreshTokens(String refreshToken);

    UserDTO resetPassword(@Valid AuthDTO authDTO);
}
