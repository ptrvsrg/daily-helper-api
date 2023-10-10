package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.ProjectDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;

import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getAllProjects(User user);

    ProjectDTO getProject(User user, String projectName);

    ProjectDTO createProject(User user, @Valid ProjectDTO projectDTO);

    ProjectDTO updateProject(User user, String projectName, @Valid ProjectDTO projectDTO);

    void deleteProject(User user, String projectName);
}
