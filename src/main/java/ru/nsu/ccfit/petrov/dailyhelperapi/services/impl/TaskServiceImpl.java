package ru.nsu.ccfit.petrov.dailyhelperapi.services.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.TaskDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.Project;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.Task;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.exceptions.ProjectNotFoundException;
import ru.nsu.ccfit.petrov.dailyhelperapi.exceptions.TaskNotFoundException;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.ProjectRepository;
import ru.nsu.ccfit.petrov.dailyhelperapi.repositories.TaskRepository;
import ru.nsu.ccfit.petrov.dailyhelperapi.services.TaskService;

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
    public List<TaskDTO> getAllTasks(User user, String projectName) {
        if (!projectRepository.existsByUserAndName(user, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }

        return taskRepository.findByProjectName(projectName)
                             .stream()
                             .map(task -> modelMapper.map(task, TaskDTO.class))
                             .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTask(User user, String projectName, Long taskId) {
        if (!projectRepository.existsByUserAndName(user, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }

        Task task = taskRepository.findByProjectNameAndId(projectName, taskId)
            .orElseThrow(() -> new TaskNotFoundException("Task #" + taskId + " not found"));

        return modelMapper.map(task, TaskDTO.class);
    }

    @Override
    public TaskDTO createTask(User user, String projectName,
                                   @Valid TaskDTO taskDTO) {
        Project project = projectRepository.findByUserAndName(user, projectName)
            .orElseThrow(() -> new ProjectNotFoundException("Project " + projectName + " not found"));

        Task task = modelMapper.map(taskDTO, Task.class);
        task.setProject(project);

        Task savedTask = taskRepository.save(task);
        log.info("User {} created task #{} of project {}", user.getEmail(), savedTask.getId(), projectName);

        return modelMapper.map(savedTask, TaskDTO.class);
    }

    @Override
    public TaskDTO updateTask(User user, String projectName, Long taskId,
                                   @Valid TaskDTO taskDTO) {
        if (!projectRepository.existsByUserAndName(user, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }

        Task task = taskRepository.findByProjectNameAndId(projectName, taskId)
            .orElseThrow(() -> new TaskNotFoundException("Task #" + taskId + " not found"));

        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());

        Task savedTask = taskRepository.save(task);
        log.info("User {} updated task #{} of project {}", user.getEmail(), savedTask.getId(), projectName);

        return modelMapper.map(savedTask, TaskDTO.class);
    }

    @Override
    public void deleteTask(User user, String projectName, Long taskId) {
        if (!projectRepository.existsByUserAndName(user, projectName)) {
            throw new ProjectNotFoundException("Project " + projectName + " not found");
        }
        if (!taskRepository.existsByProjectNameAndId(projectName, taskId)) {
            throw new TaskNotFoundException("Task #" + taskId + " not found");
        }
        taskRepository.deleteById(taskId);
        log.info("User {} deleted task #{} of project {}", user.getEmail(), taskId, projectName);
    }
}
