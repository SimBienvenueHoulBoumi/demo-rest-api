package simdev.demo.servicesImpl;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import simdev.demo.exceptions.TaskNotFoundException;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksGetOneService;
import simdev.demo.services.TasksDeleteService;

@Service
@AllArgsConstructor
public class TasksDeleteServiceImpl implements TasksDeleteService {

    private final TasksRepository tasksRepository;
    private final TasksGetOneService tasksGetOneService;

    @Override
    public void delete(Long id) {
        Tasks task = tasksGetOneService.getTaskById(id);
        if (task == null) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        tasksRepository.delete(task);
    }
}
