package simdev.demo.services;

import java.util.List;

import simdev.demo.models.Tasks;

/**
 * Service interface for get all tasks.
 */
public interface TasksGetAllService {
     /**
     * Updates a task with the provided DTO.
   
     */
    List<Tasks> getAll();
}
