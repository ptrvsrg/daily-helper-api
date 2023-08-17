package ru.nsu.ccfit.petrov.dailyhelper.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.CustomUserDetails;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.User;

@Repository
public interface UserDetailsRepository
    extends JpaRepository<CustomUserDetails, Long> {

    Optional<CustomUserDetails> findByUserEmail(String email);
}
