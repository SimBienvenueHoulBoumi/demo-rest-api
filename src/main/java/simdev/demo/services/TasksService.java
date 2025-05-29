package simdev.demo.services;

import java.util.List;

import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;

/**
 * Service interface for managing tasks.
 */
public interface TasksService {

    /**
     * Creates a new task from the provided DTO.
     * @param task the task data transfer object
     * @return the created task entity
     */
    Tasks createTask(TasksDto task);

    /**
     * Retrieves a task by its ID.
     * @param id the ID of the task to retrieve
     * @return the task entity
     */
    Tasks getTaskById(Long id);

    /**
     * Updates a task with the provided DTO.
     * @param id the ID of the task to update
     * @param task the task data transfer object
     */
    void updateTask(Long id, TasksDto task);

    /**
     * Deletes a task by its ID.
     * @param id the ID of the task to delete
     */
    void deleteTask(Long id);

    /**
     * Retrieves all tasks.
     * @return a list of all task entities
     */
    List<Tasks> getAllTasks();
}
