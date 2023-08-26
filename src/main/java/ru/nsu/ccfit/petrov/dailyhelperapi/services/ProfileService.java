package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.entities.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ChangeNameRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ChangePasswordRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(User user);

    ProfileResponse changeName(User user, @Valid ChangeNameRequest changeNameRequest);

    void changePassword(User user, @Valid ChangePasswordRequest changePasswordRequest);
}
