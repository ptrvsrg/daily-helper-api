package ru.nsu.ccfit.petrov.dailyhelper.services;

import ru.nsu.ccfit.petrov.dailyhelper.models.daos.User;

public interface VerificationTokenService {

    String createToken(User user, Boolean deleteUser);

    String getEmail(String verificationToken);

    void deleteTokens(String email);

    void deletedExpiredTokens();
}
