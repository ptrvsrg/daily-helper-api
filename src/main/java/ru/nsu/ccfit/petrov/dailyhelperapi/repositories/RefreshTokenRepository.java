package ru.nsu.ccfit.petrov.dailyhelperapi.repositories;

import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.RefreshToken;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.User;

@Repository
public interface RefreshTokenRepository
    extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenAndExpiredTimeAfter(String token, Date time);

    Optional<RefreshToken> findByUserAndExpiredTimeAfter(User user, Date time);

    void deleteAllByExpiredTimeBefore(Date time);
}
