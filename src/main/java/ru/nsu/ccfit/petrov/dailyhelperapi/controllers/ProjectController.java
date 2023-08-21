package ru.nsu.ccfit.petrov.dailyhelperapi.controllers;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.daos.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProjectRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.ProjectResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.ProjectService;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@AuthenticationPrincipal User user,
                                                         @Valid @RequestBody ProjectRequest projectDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(projectService.createProject(user, projectDto));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> showProjects(
        @AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(projectService.getAllProjects(user));
    }

    @GetMapping("/{projectName}")
    public ResponseEntity<ProjectResponse> showProject(@AuthenticationPrincipal User user,
                                                       @PathVariable String projectName) {
        return ResponseEntity.ok().body(projectService.getProject(user, projectName));
    }

    @PutMapping("/{projectName}")
    public ResponseEntity<ProjectResponse> updateProject(@AuthenticationPrincipal User user,
                                                         @PathVariable String projectName,
                                                         @Valid @RequestBody ProjectRequest projectDto) {
        return ResponseEntity.ok().body(projectService.updateProject(user, projectName, projectDto));
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<Void> deleteProject(@AuthenticationPrincipal User user,
                                              @PathVariable String projectName) {
        projectService.deleteProject(user, projectName);
        return ResponseEntity.noContent().build();
    }
}
