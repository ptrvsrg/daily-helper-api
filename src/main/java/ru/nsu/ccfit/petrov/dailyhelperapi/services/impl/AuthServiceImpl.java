package ru.nsu.ccfit.petrov.dailyhelperapi.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.CustomUserDetails;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.JwtTokens;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.LoginRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.LoginResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.RefreshTokensResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.RegisterRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.RegisterResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ResetPasswordRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.exceptions.UserAlreadyExistException;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.UserDetailsRepository;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.UserRepository;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.AuthService;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.EmailService;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.JwtTokenService;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.VerificationTokenService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl
    implements AuthService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
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
        User savedUser = userRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUser(savedUser);
        userDetails.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userDetails.setIsActive(false);

        CustomUserDetails savedUserDetails = userDetailsRepository.save(userDetails);
        log.info("User {} registered", savedUser.getEmail());

        String verificationToken = verificationTokenService.createToken(savedUser, true);
        emailService.sendWelcomeAndActivateAccount(savedUser.getEmail(), verificationToken);

        return modelMapper.map(savedUser, RegisterResponse.class);
    }

    @Override
    public void verify(String verificationToken) {
        String email = verificationTokenService.getEmail(verificationToken);
        CustomUserDetails userDetails = userDetailsRepository.findByUserEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
        userDetails.setIsActive(true);

        userDetailsRepository.save(userDetails);
        log.info("User {} activated", email);

        verificationTokenService.deleteTokens(email);
    }

    @Override
    public LoginResponse login(@Valid LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User " + loginRequest.getEmail() + " not found"));

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                    loginRequest.getPassword()));
        log.info("User {} logged in", loginRequest.getEmail());

        JwtTokens tokens = jwtTokenService.createTokens(user);

        LoginResponse loginResponse = modelMapper.map(user, LoginResponse.class);
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
        CustomUserDetails userDetails = userDetailsRepository
            .findByUserEmail(resetPasswordRequest.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User " + resetPasswordRequest.getEmail() + " not found"));

        userDetails.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userDetails.setIsActive(false);

        CustomUserDetails savedUserDetails = userDetailsRepository.save(userDetails);
        log.info("User {} reset password", savedUserDetails.getUser().getEmail());

        String verificationToken = verificationTokenService.createToken(savedUserDetails.getUser(),
                                                                        false);
        emailService.sendConfirmPasswordChange(savedUserDetails.getUser().getEmail(),
                                               verificationToken);

        return modelMapper.map(savedUserDetails.getUser(), RegisterResponse.class);
    }
}
