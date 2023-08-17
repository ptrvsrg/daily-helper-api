package ru.nsu.ccfit.petrov.dailyhelperapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.JwtTokenService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
    extends GenericFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        String accessToken = jwtTokenService.resolveAccessToken((HttpServletRequest) request);
        if (accessToken != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(
                jwtTokenService.getEmailFromAccessToken(accessToken));
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                                                          userDetails.getPassword(),
                                                                          userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
