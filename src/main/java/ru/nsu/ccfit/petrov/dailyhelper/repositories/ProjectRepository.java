package ru.nsu.ccfit.petrov.dailyhelper.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.Project;

@Repository
public interface ProjectRepository
    extends JpaRepository<Project, Long> {

    List<Project> findByUserEmail(String email);

    Optional<Project> findByUserEmailAndName(String email, String projectName);

    boolean existsByUserEmailAndName(String email, String projectName);

    void deleteByUserEmailAndName(String email, String projectName);
}
