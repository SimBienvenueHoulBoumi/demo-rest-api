package simdev.demo.services;

import java.util.List;

import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;

public interface TasksService {
    Tasks createTask(TasksDto task);
    Tasks getTaskById(Long id);
    void updateTask(Long id, TasksDto task);
    void deleteTask(Long id);
    List<Tasks> getAllTasks();
}
