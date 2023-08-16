package ru.nsu.ccfit.petrov.dailyhelper.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.Project;
import ru.nsu.ccfit.petrov.dailyhelper.models.daos.Task;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.TaskRequest;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.TaskResponse;
import ru.nsu.ccfit.petrov.dailyhelper.models.exceptions.ProjectNotFoundException;
import ru.nsu.ccfit.petrov.dailyhelper.models.exceptions.TaskNotFoundException;
import ru.nsu.ccfit.petrov.dailyhelper.repositories.ProjectRepository;
import ru.nsu.ccfit.petrov.dailyhelper.repositories.TaskRepository;
import ru.nsu.ccfit.petrov.dailyhelper.services.TaskService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl
    implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TaskResponse> getAllTasks(String email, String projectName) {
        if (!projectRepository.existsByUserEmailAndName(email, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }

        return taskRepository.findByProjectName(projectName)
                             .stream()
                             .map(task -> modelMapper.map(task, TaskResponse.class))
                             .collect(Collectors.toList());
    }

    @Override
    public TaskResponse getTask(String email, String projectName, Long taskId) {
        if (!projectRepository.existsByUserEmailAndName(email, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }

        Task task = taskRepository.findByProjectNameAndId(projectName, taskId)
            .orElseThrow(() -> new TaskNotFoundException("Task #" + taskId + " not found"));

        return modelMapper.map(task, TaskResponse.class);
    }

    @Override
    public TaskResponse createTask(String email, String projectName,
                                   @Valid TaskRequest taskRequest) {
        Project project = projectRepository.findByUserEmailAndName(email, projectName)
            .orElseThrow(() -> new ProjectNotFoundException("Project " + projectName + " not found"));

        Task task = modelMapper.map(taskRequest, Task.class);
        task.setProject(project);

        Task savedTask = taskRepository.save(task);
        log.info("User {} created task #{} of project {}", email, savedTask.getId(), projectName);

        return modelMapper.map(savedTask, TaskResponse.class);
    }

    @Override
    public TaskResponse updateTask(String email, String projectName, Long taskId,
                                   @Valid TaskRequest taskRequest) {
        if (!projectRepository.existsByUserEmailAndName(email, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }

        Task task = taskRepository.findByProjectNameAndId(projectName, taskId)
            .orElseThrow(() -> new TaskNotFoundException("Task #" + taskId + " not found"));

        task.setName(taskRequest.getName());
        task.setDescription(taskRequest.getDescription());

        Task savedTask = taskRepository.save(task);
        log.info("User {} updated task #{} of project {}", email, savedTask.getId(), projectName);

        return modelMapper.map(savedTask, TaskResponse.class);
    }

    @Override
    public void deleteTask(String email, String projectName, Long taskId) {
        if (!projectRepository.existsByUserEmailAndName(email, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }
        if (!taskRepository.existsByProjectNameAndId(projectName, taskId)) {
            throw new TaskNotFoundException("Task #" + taskId + " not found");
        }
        taskRepository.deleteById(taskId);
        log.info("User {} deleted task #{} of project {}", email, taskId, projectName);
    }
}
