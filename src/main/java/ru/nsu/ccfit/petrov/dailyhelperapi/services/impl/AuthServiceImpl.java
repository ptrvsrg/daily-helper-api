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
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.AuthDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.TokensDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.UserDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.CustomUserDetails;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.JwtTokens;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.exceptions.UserAlreadyExistException;
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
    public UserDTO register(@Valid UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistException(
                "User " + userDTO.getEmail() + " already exists");
        }

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUser(savedUser);
        userDetails.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDetails.setIsActive(false);

        CustomUserDetails savedUserDetails = userDetailsRepository.save(userDetails);
        log.info("User {} registered", savedUser.getEmail());

        String verificationToken = verificationTokenService.createToken(savedUser, true);
        emailService.sendWelcomeAndActivateAccount(savedUser.getEmail(), verificationToken);

        return modelMapper.map(savedUser, UserDTO.class);
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
    public TokensDTO login(@Valid AuthDTO authDTO) {
        User user = userRepository.findByEmail(authDTO.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User " + authDTO.getEmail() + " not found"));

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
        log.info("User {} logged in", authDTO.getEmail());

        JwtTokens tokens = jwtTokenService.createTokens(user);
        return modelMapper.map(tokens, TokensDTO.class);
    }

    @Override
    public TokensDTO refreshTokens(String refreshToken) {
        JwtTokens tokens = jwtTokenService.refreshTokens(refreshToken);
        return modelMapper.map(tokens, TokensDTO.class);
    }

    @Override
    public UserDTO resetPassword(@Valid AuthDTO authDTO) {
        CustomUserDetails userDetails = userDetailsRepository
            .findByUserEmail(authDTO.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User " + authDTO.getEmail() + " not found"));

        userDetails.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        userDetails.setIsActive(false);

        CustomUserDetails savedUserDetails = userDetailsRepository.save(userDetails);
        log.info("User {} reset password", savedUserDetails.getUser().getEmail());

        String verificationToken = verificationTokenService.createToken(savedUserDetails.getUser(),
                                                                        false);
        emailService.sendConfirmPasswordChange(savedUserDetails.getUser().getEmail(),
                                               verificationToken);

        return modelMapper.map(savedUserDetails.getUser(), UserDTO.class);
    }
}
