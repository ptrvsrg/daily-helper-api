package ru.nsu.ccfit.petrov.dailyhelper.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.VerificationToken;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.User;

@Repository
public interface VerificationTokenRepository
    extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByTokenAndExpiredTimeAfter(String token, Date time);

    Optional<VerificationToken> findByUserAndExpiredTimeAfter(User user, Date time);

    List<VerificationToken> findAllByExpiredTimeBefore(Date time);

    void deleteByUser(User user);
}
