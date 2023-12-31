package ru.nsu.ccfit.petrov.dailyhelperapi.services;

public interface EmailService {

    void sendMessage(String to, String subject, String text);

    void sendWelcomeAndActivateAccount(String to, String token);

    void sendConfirmPasswordChange(String to, String token);
}
