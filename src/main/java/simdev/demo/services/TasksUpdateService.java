package simdev.demo.services;

import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;

/**
 * Service interface for updating tasks.
 */
public interface TasksUpdateService {
    /**
     * Updates a task with the provided DTO.
     * @param id the ID of the task to update
     * @param task the task data transfer object
     */
    public Tasks update(Long id, TasksDto task);
}
