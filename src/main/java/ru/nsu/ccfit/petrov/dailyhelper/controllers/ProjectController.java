package ru.nsu.ccfit.petrov.dailyhelper.controllers;

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
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ProjectRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ProjectResponse;
import ru.nsu.ccfit.petrov.dailyhelper.services.ProjectService;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> showProjects(
        @AuthenticationPrincipal String email) {
        return ResponseEntity.ok().body(projectService.getAllProjects(email));
    }

    @GetMapping("/{projectName}")
    public ResponseEntity<ProjectResponse> showProject(@AuthenticationPrincipal String email,
                                                       @PathVariable String projectName) {
        return ResponseEntity.ok().body(projectService.getProject(email, projectName));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@AuthenticationPrincipal String email,
                                                         @Valid @RequestBody ProjectRequest projectDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(projectService.createProject(email, projectDto));
    }

    @PutMapping("/{projectName}")
    public ResponseEntity<ProjectResponse> updateProject(@AuthenticationPrincipal String email,
                                                         @PathVariable String projectName,
                                                         @Valid @RequestBody ProjectRequest projectDto) {
        return ResponseEntity.ok().body(projectService.updateProject(email, projectName, projectDto));
    }

    @DeleteMapping("/{projectName}")
    public ResponseEntity<Void> deleteProject(@AuthenticationPrincipal String email,
                                              @PathVariable String projectName) {
        projectService.deleteProject(email, projectName);
        return ResponseEntity.noContent().build();
    }
}
