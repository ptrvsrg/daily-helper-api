package ru.nsu.ccfit.petrov.dailyhelper.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelper.repositories.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl
    implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
    }
}
