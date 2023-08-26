package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import java.util.List;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.entities.User;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.TaskRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.TaskResponse;

public interface TaskService {

    List<TaskResponse> getAllTasks(User user, String projectName);

    TaskResponse getTask(User user, String projectName, Long taskId);

    TaskResponse createTask(User user, String projectName, @Valid TaskRequest taskRequest);

    TaskResponse updateTask(User user, String projectName, Long taskId,
                            @Valid TaskRequest taskUpdateRequest);

    void deleteTask(User user, String projectName, Long taskId);
}
