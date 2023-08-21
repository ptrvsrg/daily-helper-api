package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import java.util.List;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProjectRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProjectResponse;

public interface ProjectService {

    List<ProjectResponse> getAllProjects(User user);

    ProjectResponse getProject(User user, String projectName);

    ProjectResponse createProject(User user, @Valid ProjectRequest projectRequest);

    ProjectResponse updateProject(User user, String projectName,
                                  @Valid ProjectRequest projectUpdateRequest);

    void deleteProject(User user, String projectName);
}
