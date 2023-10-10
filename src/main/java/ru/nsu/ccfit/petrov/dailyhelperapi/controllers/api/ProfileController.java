package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.ChangeNameRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.ChangePasswordRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses.ProfileResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.ChangeNameDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.ChangePasswordDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.UserDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.ProfileService;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ProfileResponse> showProfile(@AuthenticationPrincipal User user) {
        UserDTO userDTO = profileService.getProfile(user);
        return ResponseEntity.ok().body(modelMapper.map(userDTO, ProfileResponse.class));
    }

    @PutMapping("/edit/name")
    public ResponseEntity<ProfileResponse> changeName(@AuthenticationPrincipal User user,
            @Valid @RequestBody ChangeNameRequest changeNameRequest) {
        UserDTO userDTO = profileService.changeName(user, modelMapper.map(changeNameRequest, ChangeNameDTO.class));
        return ResponseEntity.ok().body(modelMapper.map(userDTO, ProfileResponse.class));
    }

    @PutMapping("/edit/password")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal User user,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        profileService.changePassword(user, modelMapper.map(changePasswordRequest, ChangePasswordDTO.class));
        return ResponseEntity.ok().build();
    }
}
