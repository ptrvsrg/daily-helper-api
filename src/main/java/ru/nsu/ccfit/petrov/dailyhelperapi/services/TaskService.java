package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import java.util.List;

import ru.nsu.ccfit.petrov.dailyhelperapi.dtos.TaskDTO;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.requests.TaskRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.controllers.responses.TaskResponse;

public interface TaskService {

    List<TaskDTO> getAllTasks(User user, String projectName);

    TaskDTO getTask(User user, String projectName, Long taskId);

    TaskDTO createTask(User user, String projectName, @Valid TaskDTO taskDTO);

    TaskDTO updateTask(User user, String projectName, Long taskId, @Valid TaskDTO taskDTO);

    void deleteTask(User user, String projectName, Long taskId);
}
