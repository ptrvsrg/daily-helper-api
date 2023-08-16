package ru.nsu.ccfit.petrov.dailyhelper.services;

import jakarta.validation.Valid;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ChangeNameRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(String email);

    ProfileResponse changeName(String email, @Valid ChangeNameRequest changeNameRequest);
}
