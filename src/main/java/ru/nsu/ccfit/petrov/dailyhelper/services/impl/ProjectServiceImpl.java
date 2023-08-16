package ru.nsu.ccfit.petrov.dailyhelper.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.Project;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.User;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ProjectRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ProjectResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.exceptions.ProjectAlreadyExistException;
import ru.nsu.ccfit.petrov.dailyhelper.models.exceptions.ProjectNotFoundException;
import ru.nsu.ccfit.petrov.dailyhelper.repositories.ProjectRepository;
import ru.nsu.ccfit.petrov.dailyhelper.repositories.UserRepository;
import ru.nsu.ccfit.petrov.dailyhelper.services.ProjectService;

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
    public List<ProjectResponse> getAllProjects(String email) {
        return projectRepository.findByUserEmail(email)
                                .stream()
                                .map(project -> modelMapper.map(project, ProjectResponse.class))
                                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse getProject(String email, String projectName) {
        Project project = projectRepository.findByUserEmailAndName(email, projectName)
            .orElseThrow(() -> new ProjectNotFoundException("Project " + projectName + " not found"));
        return modelMapper.map(project, ProjectResponse.class);
    }

    @Override
    public ProjectResponse createProject(String email, @Valid ProjectRequest projectRequest) {
        if (projectRepository.existsByUserEmailAndName(email, projectRequest.getName())) {
            throw new ProjectAlreadyExistException(
                "Project " + projectRequest.getName() + " already exists");
        }

        Project project = modelMapper.map(projectRequest, Project.class);

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
        project.setUser(user);

        Project savedProject = projectRepository.save(project);
        log.info("User {} created project {}", email, project.getName());

        return modelMapper.map(savedProject, ProjectResponse.class);
    }

    @Override
    public ProjectResponse updateProject(String email, String projectName,
                                         @Valid ProjectRequest projectRequest) {
        Project project = projectRepository.findByUserEmailAndName(email, projectName)
            .orElseThrow(() -> new ProjectNotFoundException("Project " + projectName + " not found"));

        if (projectRepository.existsByUserEmailAndName(email, projectRequest.getName())) {
            throw new ProjectAlreadyExistException(
                "Project " + projectRequest.getName() + " already exists");
        }
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());

        Project savedProject = projectRepository.save(project);
        log.info("User {} updated project {}", email, projectName);

        return modelMapper.map(savedProject, ProjectResponse.class);
    }

    @Override
    public void deleteProject(String email, String projectName) {
        if (!projectRepository.existsByUserEmailAndName(email, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }
        projectRepository.deleteByUserEmailAndName(email, projectName);
        log.info("User {} deleted project {}", email, projectName);
    }
}
