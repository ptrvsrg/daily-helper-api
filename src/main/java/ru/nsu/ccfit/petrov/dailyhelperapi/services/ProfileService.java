package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.ChangeNameDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.ChangePasswordDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.UserDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.ChangeNameRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.ChangePasswordRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses.ProfileResponse;

public interface ProfileService {

    UserDTO getProfile(User user);

    UserDTO changeName(User user, @Valid ChangeNameDTO changeNameDTO);

    void changePassword(User user, @Valid ChangePasswordDTO changePasswordDTO);
}
