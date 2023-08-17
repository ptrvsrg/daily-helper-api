package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.User;

public interface VerificationTokenService {

    String createToken(User user, Boolean deleteUser);

    String getEmail(String verificationToken);

    void deleteTokens(String email);

    void deletedExpiredTokens();
}
