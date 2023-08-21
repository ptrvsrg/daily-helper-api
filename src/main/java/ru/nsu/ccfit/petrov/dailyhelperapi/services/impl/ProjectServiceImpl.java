package ru.nsu.ccfit.petrov.dailyhelperapi.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.Project;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProjectRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProjectResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.exceptions.ProjectAlreadyExistException;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.exceptions.ProjectNotFoundException;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.ProjectRepository;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.UserRepository;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.ProjectService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl
    implements ProjectService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProjectResponse> getAllProjects(User user) {
        return projectRepository.findByUser(user)
                                .stream()
                                .map(project -> modelMapper.map(project, ProjectResponse.class))
                                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse getProject(User user, String projectName) {
        Project project = projectRepository.findByUserAndName(user, projectName)
            .orElseThrow(() -> new ProjectNotFoundException("Project " + projectName + " not found"));
        return modelMapper.map(project, ProjectResponse.class);
    }

    @Override
    public ProjectResponse createProject(User user, @Valid ProjectRequest projectRequest) {
        if (projectRepository.existsByUserAndName(user, projectRequest.getName())) {
            throw new ProjectAlreadyExistException(
                "Project " + projectRequest.getName() + " already exists");
        }

        Project project = modelMapper.map(projectRequest, Project.class);
        project.setUser(user);

        Project savedProject = projectRepository.save(project);
        log.info("User {} created project {}", user.getEmail(), project.getName());

        return modelMapper.map(savedProject, ProjectResponse.class);
    }

    @Override
    public ProjectResponse updateProject(User user, String projectName,
                                         @Valid ProjectRequest projectRequest) {
        Project project = projectRepository.findByUserAndName(user, projectName)
            .orElseThrow(() -> new ProjectNotFoundException("Project " + projectName + " not found"));

        if (!Objects.equals(projectName, projectRequest.getName())
            && projectRepository.existsByUserAndName(user, projectRequest.getName())) {
            throw new ProjectAlreadyExistException(
                "Project " + projectRequest.getName() + " already exists");
        }
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());

        Project savedProject = projectRepository.save(project);
        log.info("User {} updated project {}", user.getEmail(), projectName);

        return modelMapper.map(savedProject, ProjectResponse.class);
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
