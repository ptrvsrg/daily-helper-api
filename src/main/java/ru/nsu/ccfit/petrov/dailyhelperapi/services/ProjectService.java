package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import java.util.List;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProjectRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProjectResponse;

public interface ProjectService {

    List<ProjectResponse> getAllProjects(String email);

    ProjectResponse getProject(String email, String projectName);

    ProjectResponse createProject(String email, @Valid ProjectRequest projectRequest);

    ProjectResponse updateProject(String email, String projectName,
                                  @Valid ProjectRequest projectUpdateRequest);

    void deleteProject(String email, String projectName);
}