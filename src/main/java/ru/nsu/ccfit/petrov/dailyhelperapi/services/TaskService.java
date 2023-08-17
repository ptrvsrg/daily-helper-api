package ru.nsu.ccfit.petrov.dailyhelperapi.services;

import jakarta.validation.Valid;
import java.util.List;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.TaskRequest;
import ru.nsu.ccfit.petrov.dailyhelperapi.models.dtos.TaskResponse;

public interface TaskService {

    List<TaskResponse> getAllTasks(String email, String projectName);

    TaskResponse getTask(String email, String projectName, Long taskId);

    TaskResponse createTask(String email, String projectName, @Valid TaskRequest taskRequest);

    TaskResponse updateTask(String email, String projectName, Long taskId,
                            @Valid TaskRequest taskUpdateRequest);

    void deleteTask(String email, String projectName, Long taskId);
}
