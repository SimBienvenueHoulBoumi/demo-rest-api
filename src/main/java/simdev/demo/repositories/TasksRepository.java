package simdev.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import simdev.demo.models.Tasks;

import java.util.Optional;

/**
 * Repository for accessing and managing Tasks entities.
 */
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    /**
     * Finds a task by its name.
     * @param name the name of the task to find
     * @return an Optional containing the task, or empty if not found
     */
    Optional<Tasks> findByName(String name);
}
