package ru.nsu.ccfit.petrov.dailyhelperapi.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.ProjectDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.exceptions.ProjectAlreadyExistException;
import ru.nsu.ccfit.petrov.dailyhelperapi.exceptions.ProjectNotFoundException;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.Project;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.ProjectRepository;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.ProjectService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl
        implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProjectDTO> getAllProjects(User user) {
        return projectRepository.findByUser(user)
                .stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProject(User user, String projectName) {
        Project project = projectRepository.findByUserAndName(user, projectName)
                .orElseThrow(() -> new ProjectNotFoundException("Project " + projectName + " not found"));
        return modelMapper.map(project, ProjectDTO.class);
    }

    @Override
    public ProjectDTO createProject(User user, @Valid ProjectDTO projectDTO) {
        if (projectRepository.existsByUserAndName(user, projectDTO.getName())) {
            throw new ProjectAlreadyExistException(
                    "Project " + projectDTO.getName() + " already exists");
        }

        Project project = modelMapper.map(projectDTO, Project.class);
        project.setUser(user);

        Project savedProject = projectRepository.save(project);
        log.info("User {} created project {}", user.getEmail(), project.getName());

        return modelMapper.map(savedProject, ProjectDTO.class);
    }

    @Override
    public ProjectDTO updateProject(User user, String projectName, @Valid ProjectDTO projectDTO) {
        Project project = projectRepository.findByUserAndName(user, projectName)
                .orElseThrow(() -> new ProjectNotFoundException("Project " + projectName + " not found"));

        if (!Objects.equals(projectName, projectDTO.getName())
                && projectRepository.existsByUserAndName(user, projectDTO.getName())) {
            throw new ProjectAlreadyExistException(
                    "Project " + projectDTO.getName() + " already exists");
        }
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());

        Project savedProject = projectRepository.save(project);
        log.info("User {} updated project {}", user.getEmail(), projectName);

        return modelMapper.map(savedProject, ProjectDTO.class);
    }

    @Override
    public void deleteProject(User user, String projectName) {
        if (!projectRepository.existsByUserAndName(user, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }
        projectRepository.deleteByUserAndName(user, projectName);
        log.info("User {} deleted project {}", user.getEmail(), projectName);
    }
}
