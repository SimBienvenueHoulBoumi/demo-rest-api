package simdev.demo.services;

/**
 * Service interface for deleting tasks.
 */
public interface TasksDeleteService {
    /**
     * Deletes a task by its ID.
     * @param id the ID of the task to delete
     */
    void delete(Long id);
}
