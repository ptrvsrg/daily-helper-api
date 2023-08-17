package ru.nsu.ccfit.petrov.dailyhelper.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.LoginRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.LoginResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.RefreshTokensResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.RegisterRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.RegisterResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ResetPasswordRequest;
import ru.nsu.ccfit.petrov.dailyhelper.services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
        @Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(authService.login(loginRequest));
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verify(@RequestParam String token) {
        authService.verify(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/refresh-tokens")
    public ResponseEntity<RefreshTokensResponse> refreshTokens(@RequestParam String token) {
        return ResponseEntity.ok().body(authService.refreshTokens(token));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<RegisterResponse> resetPassword(
        @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok().body(authService.resetPassword(resetPasswordRequest));
    }
}
