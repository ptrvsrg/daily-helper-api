package ru.nsu.ccfit.petrov.dailyhelper.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.JwtTokens;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.RefreshToken;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.User;
import ru.nsu.ccfit.petrov.dailyhelper.models.exceptions.JwtTokenException;
import ru.nsu.ccfit.petrov.dailyhelper.repositories.RefreshTokenRepository;
import ru.nsu.ccfit.petrov.dailyhelper.repositories.UserRepository;
import ru.nsu.ccfit.petrov.dailyhelper.services.JwtTokenService;

@Component
@Transactional
@RequiredArgsConstructor
public class JwtTokenServiceImpl
    implements JwtTokenService {

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.access-token.secret}")
    private String SECRET;

    @Value("${jwt.access-token.expired-time}")
    private Long ACCESS_TOKEN_EXPIRED_TIME;

    @Value("${jwt.refresh-token.expired-time}")
    private Long REFRESH_TOKEN_EXPIRED_TIME;

    @PostConstruct
    protected void initSecret() {
        SECRET = Base64.getEncoder()
                       .encodeToString(SECRET.getBytes());
    }

    @Override
    public JwtTokens createTokens(User user) {
        return new JwtTokens(createAccessToken(user), createRefreshToken(user));
    }

    @Override
    public String resolveAccessToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public String getEmailFromAccessToken(String accessToken) {
        try {
            return Jwts.parser()
                       .setSigningKey(SECRET)
                       .parseClaimsJws(accessToken)
                       .getBody()
                       .getSubject();
        } catch (JwtException e) {
            throw new JwtTokenException("Access token " + accessToken + " not valid or outdated");
        }
    }

    @Override
    public Date getExpirationFromAccessToken(String accessToken) {
        try {
            return Jwts.parser()
                       .setSigningKey(SECRET)
                       .parseClaimsJws(accessToken)
                       .getBody()
                       .getExpiration();
        } catch (JwtException e) {
            throw new JwtTokenException("Access token " + accessToken + " not valid or outdated");
        }
    }

    @Override
    public JwtTokens refreshTokens(String refreshToken) {
        RefreshToken token = refreshTokenRepository
            .findByTokenAndExpiredTimeAfter(refreshToken, new Date())
            .orElseThrow(() -> new JwtTokenException("Refresh token " + refreshToken + " not valid or outdated"));
        JwtTokens tokens = createTokens(token.getUser());
        return tokens;
    }

    private String createAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());

        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED_TIME))
                   .signWith(SignatureAlgorithm.HS256, SECRET)
                   .compact();
    }

    private String createRefreshToken(User user) {
        RefreshToken token = refreshTokenRepository
            .findByUserAndExpiredTimeAfter(user, new Date())
            .orElse(new RefreshToken());
        token.setUser(user);
        token.setExpiredTime(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED_TIME));
        token.setToken(UUID.randomUUID().toString());

        RefreshToken savedToken = refreshTokenRepository.save(token);
        return savedToken.getToken();
    }

    @Override
    @Async
    @Scheduled(fixedDelayString = "${jwt.refresh-token.expired-time}")
    public void deletedExpiredRefreshTokens() {
        refreshTokenRepository.deleteAllByExpiredTimeBefore(new Date());
    }
}
