package simdev.demo.servicesImpl;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksGetOneService;
import simdev.demo.services.TasksUpdateService;

@Service
@AllArgsConstructor
public class TasksUpdateServiceImpl implements TasksUpdateService {

    private final TasksRepository tasksRepository;
    private final TasksGetOneService tasksGetOneService;

    @Override
    public Tasks update(Long id, TasksDto taskDto) {
        Tasks existingTask = tasksGetOneService.getTaskById(id);

        existingTask.setName(taskDto.getName());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(taskDto.getStatus());

        return tasksRepository.save(existingTask);
    }
}
