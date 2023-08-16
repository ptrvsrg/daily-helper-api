package ru.nsu.ccfit.petrov.dailyhelper.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ChangeNameRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ProfileResponse;
import ru.nsu.ccfit.petrov.dailyhelper.services.ProfileService;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> showProfile(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok().body(profileService.getProfile(email));
    }

    @PutMapping("/edit/name")
    public ResponseEntity<ProfileResponse> changeName(@AuthenticationPrincipal String email,
                                                      @Valid @RequestBody ChangeNameRequest changeNameRequest) {
        return ResponseEntity.ok().body(profileService.changeName(email, changeNameRequest));
    }
}