package ru.nsu.ccfit.petrov.dailyhelper.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.User;

@Repository
public interface UserRepository
    extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
