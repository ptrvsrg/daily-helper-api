package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ChangeNameRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(String email);

    ProfileResponse changeName(String email, @Valid ChangeNameRequest changeNameRequest);
}
