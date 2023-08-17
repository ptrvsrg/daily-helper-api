package ru.nsu.ccfit.petrov.dailyhelperapi.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.UserDetailsRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl
    implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {
        return userDetailsRepository.findByUserEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
    }
}
