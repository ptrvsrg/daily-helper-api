package ru.nsu.ccfit.petrov.dailyhelperapi.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ChangeNameRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProfileResponse;
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
    public ProfileResponse getProfile(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
        return modelMapper.map(user, ProfileResponse.class);
    }

    @Override
    public ProfileResponse changeName(String email, @Valid ChangeNameRequest changeNameRequest) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));

        user.setFirstName(changeNameRequest.getFirstName());
        user.setLastName(changeNameRequest.getLastName());

        User savedUser = userRepository.save(user);
        log.info("User {} updated profile", savedUser.getEmail());

        return modelMapper.map(savedUser, ProfileResponse.class);
    }

    @Override
    public void changePassword(User user, @Valid ChangePasswordRequest changePasswordRequest) {
        CustomUserDetails userDetails = userDetailsRepository.findById(user.getId())
            .orElseThrow(() -> new UsernameNotFoundException("User " + user.getEmail() + " not found"));

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        userDetails.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userDetailsRepository.save(userDetails);
        log.info("User {} updated password", userDetails.getUser().getEmail());
    }
}
