package ru.nsu.ccfit.petrov.dailyhelperapi.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.Project;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;

@Repository
public interface ProjectRepository
    extends JpaRepository<Project, Long> {

    List<Project> findByUser(User user);

    Optional<Project> findByUserAndName(User user, String projectName);

    boolean existsByUserAndName(User user, String projectName);

    void deleteByUserAndName(User user, String projectName);
}
