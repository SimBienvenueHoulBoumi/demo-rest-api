package simdev.demo.servicesImpl;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import simdev.demo.exceptions.TaskNotFoundException;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksGetOneService;

@Service
@AllArgsConstructor
public class TasksGetOneServiceImpl implements TasksGetOneService {

    private final TasksRepository tasksRepository;

    @Override
    public Tasks getTaskById(Long id) {
        return tasksRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }
}
