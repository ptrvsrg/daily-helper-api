package ru.nsu.ccfit.petrov.dailyhelperapi.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.entities.CustomUserDetails;

@Repository
public interface UserDetailsRepository
    extends JpaRepository<CustomUserDetails, Long> {

    Optional<CustomUserDetails> findByUserEmail(String email);
}
