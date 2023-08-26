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
import ru.nsu.ccfit.petrov.dailyhelperapi.models.entities.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.TaskRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.TaskResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectName}/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@AuthenticationPrincipal User user,
                                                   @PathVariable String projectName,
                                                   @Valid @RequestBody TaskRequest taskDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(taskService.createTask(user, projectName, taskDto));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> showTasks(@AuthenticationPrincipal User user,
                                                        @PathVariable String projectName) {
        return ResponseEntity.ok().body(taskService.getAllTasks(user, projectName));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> showTask(@AuthenticationPrincipal User user,
                                                 @PathVariable String projectName,
                                                 @PathVariable Long taskId) {
        return ResponseEntity.ok().body(taskService.getTask(user, projectName, taskId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@AuthenticationPrincipal User user,
                                                   @PathVariable String projectName,
                                                   @PathVariable Long taskId,
                                                   @Valid @RequestBody TaskRequest taskDto) {
        return ResponseEntity.ok().body(taskService.updateTask(user, projectName, taskId, taskDto));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal User user,
                                           @PathVariable String projectName,
                                           @PathVariable Long taskId) {
        taskService.deleteTask(user, projectName, taskId);
        return ResponseEntity.noContent().build();
    }
}
