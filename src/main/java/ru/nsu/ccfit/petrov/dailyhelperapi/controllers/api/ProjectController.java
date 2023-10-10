package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.ProjectRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses.ProjectResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.ProjectDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@AuthenticationPrincipal User user,
            @Valid @RequestBody ProjectRequest projectRequest) {
        ProjectDTO projectDTO = projectService.createProject(user, modelMapper.map(projectRequest, ProjectDTO.class));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(projectDTO, ProjectResponse.class));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> showProjects(@AuthenticationPrincipal User user) {
        List<ProjectDTO> projectDTOs = projectService.getAllProjects(user);
        return ResponseEntity.ok()
                .body(projectDTOs
                        .stream()
                        .map(projectDTO -> modelMapper.map(projectDTO, ProjectResponse.class))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{projectName}")
    public ResponseEntity<ProjectResponse> showProject(@AuthenticationPrincipal User user,
            @PathVariable String projectName) {
        ProjectDTO projectDTO = projectService.getProject(user, projectName);
        return ResponseEntity.ok().body(modelMapper.map(projectDTO, ProjectResponse.class));
    }

    @PutMapping("/{projectName}")
    public ResponseEntity<ProjectResponse> updateProject(@AuthenticationPrincipal User user,
            @PathVariable String projectName, @Valid @RequestBody ProjectRequest projectRequest) {
        ProjectDTO projectDTO =
                projectService.updateProject(user, projectName, modelMapper.map(projectRequest, ProjectDTO.class));
        return ResponseEntity.ok().body(modelMapper.map(projectDTO, ProjectResponse.class));
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<Void> deleteProject(@AuthenticationPrincipal User user, @PathVariable String projectName) {
        projectService.deleteProject(user, projectName);
        return ResponseEntity.noContent().build();
    }
}
