package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.LoginRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.RegisterRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses.ProfileResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses.TokensResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.AuthDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.TokensDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.UserDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<ProfileResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDTO userDTO = authService.register(modelMapper.map(registerRequest, UserDTO.class));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(userDTO, ProfileResponse.class));
    }

    @PostMapping("/login")
    public ResponseEntity<TokensResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokensDTO tokensDTO = authService.login(modelMapper.map(loginRequest, AuthDTO.class));
        return ResponseEntity.ok()
                .body(modelMapper.map(tokensDTO, TokensResponse.class));
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verify(@RequestParam(name = "token") String verificationToken) {
        authService.verify(verificationToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/refresh-tokens")
    public ResponseEntity<TokensResponse> refreshTokens(@RequestParam(name = "token") String refreshToken) {
        TokensDTO tokensDTO = authService.refreshTokens(refreshToken);
        return ResponseEntity.ok()
                .body(modelMapper.map(tokensDTO, TokensResponse.class));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ProfileResponse> resetPassword(
            @Valid @RequestBody LoginRequest resetPasswordRequest) {
        UserDTO userDTO = authService.resetPassword(modelMapper.map(resetPasswordRequest, AuthDTO.class));
        return ResponseEntity.ok()
                .body(modelMapper.map(userDTO, ProfileResponse.class));
    }
}
