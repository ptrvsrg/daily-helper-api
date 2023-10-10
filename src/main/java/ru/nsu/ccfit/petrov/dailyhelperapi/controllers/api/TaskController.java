package ru.nsu.ccfit.petrov.dailyhelperapi.controllers.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.TaskRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses.TaskResponse;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.TaskDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects/{projectName}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@AuthenticationPrincipal User user, @PathVariable String projectName,
            @Valid @RequestBody TaskRequest taskRequest) {
        TaskDTO taskDTO = taskService.createTask(user, projectName, modelMapper.map(taskRequest, TaskDTO.class));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(taskDTO, TaskResponse.class));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> showTasks(@AuthenticationPrincipal User user,
            @PathVariable String projectName) {
        List<TaskDTO> taskDTOs = taskService.getAllTasks(user, projectName);
        return ResponseEntity.ok()
                .body(taskDTOs
                        .stream()
                        .map(taskDTO -> modelMapper.map(taskDTO, TaskResponse.class))
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> showTask(@AuthenticationPrincipal User user, @PathVariable String projectName,
            @PathVariable Long taskId) {
        TaskDTO taskDTO = taskService.getTask(user, projectName, taskId);
        return ResponseEntity.ok()
                .body(modelMapper.map(taskDTO, TaskResponse.class));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@AuthenticationPrincipal User user, @PathVariable String projectName,
            @PathVariable Long taskId, @Valid @RequestBody TaskRequest taskRequest) {
        TaskDTO taskDTO =
                taskService.updateTask(user, projectName, taskId, modelMapper.map(taskRequest, TaskDTO.class));
        return ResponseEntity.ok()
                .body(modelMapper.map(taskDTO, TaskResponse.class));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal User user, @PathVariable String projectName,
            @PathVariable Long taskId) {
        taskService.deleteTask(user, projectName, taskId);
        return ResponseEntity.noContent().build();
    }
}
