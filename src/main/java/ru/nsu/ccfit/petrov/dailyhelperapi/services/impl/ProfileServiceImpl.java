package ru.nsu.ccfit.petrov.dailyhelperapi.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.ChangeNameDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.ChangePasswordDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.UserDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.CustomUserDetails;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.ChangeNameRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.ChangePasswordRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses.ProfileResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.UserDetailsRepository;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.UserRepository;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.ProfileService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl
    implements ProfileService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO getProfile(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO changeName(User user, @Valid ChangeNameDTO changeNameDTO) {
        user.setFirstName(changeNameDTO.getFirstName());
        user.setLastName(changeNameDTO.getLastName());

        User savedUser = userRepository.save(user);
        log.info("User {} updated profile", savedUser.getEmail());

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public void changePassword(User user, @Valid ChangePasswordDTO changePasswordDTO) {
        CustomUserDetails userDetails = userDetailsRepository.findById(user.getId())
            .orElseThrow(() -> new UsernameNotFoundException("User " + user.getEmail() + " not found"));

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        userDetails.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userDetailsRepository.save(userDetails);
        log.info("User {} updated password", userDetails.getUser().getEmail());
    }
}
