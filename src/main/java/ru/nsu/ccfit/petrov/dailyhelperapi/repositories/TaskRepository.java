package ru.nsu.ccfit.petrov.dailyhelperapi.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.Task;

@Repository
public interface TaskRepository
    extends JpaRepository<Task, Long> {

    List<Task> findByProjectName(String projectName);

    Optional<Task> findByProjectNameAndId(String projectName, Long id);

    boolean existsByProjectNameAndId(String projectName, Long id);
}
