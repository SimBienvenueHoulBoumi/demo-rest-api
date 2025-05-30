package simdev.demo.services;

import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;


/**
 * Service interface for creating tasks.
 */
public interface TasksCreationService {
    /**
     * Creates a new task from the provided DTO.
     * @param task the task data transfer object
     * @return the created task entity
     */
    Tasks create(TasksDto newTask);
}

