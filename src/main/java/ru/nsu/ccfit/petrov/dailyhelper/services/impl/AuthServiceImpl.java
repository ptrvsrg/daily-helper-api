package ru.nsu.ccfit.petrov.dailyhelper.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.JwtTokens;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.User;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.LoginRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.LoginResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ResetPasswordRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.RefreshTokensResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.RegisterRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.RegisterResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.exceptions.UserAlreadyExistException;
import ru.nsu.ccfit.petrov.dailyhelper.repositories.UserRepository;
import ru.nsu.ccfit.petrov.dailyhelper.services.VerificationTokenService;
import ru.nsu.ccfit.petrov.dailyhelper.services.AuthService;
import ru.nsu.ccfit.petrov.dailyhelper.services.JwtTokenService;
import ru.nsu.ccfit.petrov.dailyhelper.services.EmailService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl
    implements AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenService verificationTokenService;
    private final JwtTokenService jwtTokenService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(@Valid RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistException(
                "User " + registerRequest.getEmail() + " already exists");
        }

        User user = modelMapper.map(registerRequest, User.class);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setIsActive(false);

        User savedUser = userRepository.save(user);
        log.info("User {} registered", savedUser.getEmail());

        String verificationToken = verificationTokenService.createToken(savedUser, true);
        emailService.sendWelcomeAndActivateAccount(savedUser.getEmail(), verificationToken);

        return modelMapper.map(savedUser, RegisterResponse.class);
    }

    @Override
    public void verify(String verificationToken) {
        String email = verificationTokenService.getEmail(verificationToken);
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        log.info("User {} activated", savedUser.getEmail());

        verificationTokenService.deleteTokens(savedUser);
    }

    @Override
    public LoginResponse login(@Valid LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User " + loginRequest.getEmail() + " not found"));

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                    loginRequest.getPassword()));
        log.info("User {} logged in", user.getEmail());

        LoginResponse loginResponse = modelMapper.map(user, LoginResponse.class);

        JwtTokens tokens = jwtTokenService.createTokens(user);
        loginResponse.setAccessToken(tokens.getAccessToken());
        loginResponse.setRefreshToken(tokens.getRefreshToken());
        return loginResponse;
    }

    @Override
    public RefreshTokensResponse refreshTokens(String refreshToken) {
        JwtTokens tokens = jwtTokenService.refreshTokens(refreshToken);
        log.info("Created a new pair of JWT tokens by refresh token {}", refreshToken);
        return modelMapper.map(tokens, RefreshTokensResponse.class);
    }

    @Override
    public RegisterResponse resetPassword(@Valid ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findByEmail(resetPasswordRequest.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User " + resetPasswordRequest.getEmail() + " not found"));

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        user.setIsActive(false);

        User savedUser = userRepository.save(user);
        log.info("User {} reset password", savedUser.getEmail());

        String verificationToken = verificationTokenService.createToken(savedUser, false);
        emailService.sendConfirmPasswordChange(savedUser.getEmail(), verificationToken);

        return modelMapper.map(savedUser, RegisterResponse.class);
    }
}
