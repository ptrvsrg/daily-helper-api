package ru.nsu.ccfit.petrov.dailyhelperapi.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.VerificationToken;

@Repository
public interface VerificationTokenRepository
    extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByTokenAndExpiredTimeAfter(String token, Date time);

    Optional<VerificationToken> findByUserAndExpiredTimeAfter(User user, Date time);

    List<VerificationToken> findAllByExpiredTimeBefore(Date time);

    void deleteByUserEmail(String email);
}
