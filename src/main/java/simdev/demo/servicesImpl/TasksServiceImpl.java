package simdev.demo.servicesImpl;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import simdev.demo.dto.TasksDto;
import simdev.demo.exceptions.TaskNotFoundException;
import simdev.demo.mapper.TasksMapper;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksService;

@Service
@AllArgsConstructor
public final class TasksServiceImpl implements TasksService {

    private final TasksRepository tasksRepository;
    private final TasksMapper tasksMapper;

    private final String TASK_NOT_FOUND= "Task not found with id: ";

    @Override
    public Tasks createTask(final TasksDto newTask) {
        if (tasksRepository.findByName(newTask.getName()).isPresent()) {
            throw new TaskNotFoundException(
                "Task already exists with name: " + newTask.getName()
            );
        }

        Tasks task = tasksMapper.toEntity(newTask);
        return tasksRepository.save(task);
    }

    @Override
    public Tasks getTaskById(final Long id) {
        return tasksRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(
                TASK_NOT_FOUND + id));
    }

    @Override
    public void updateTask(final Long id, final TasksDto taskDto) {
        Tasks existingTask = tasksRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(
                TASK_NOT_FOUND + id));

        existingTask.setName(taskDto.getName());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(taskDto.getStatus());
        existingTask.setUpdatedAt(Date.from(Instant.now()));

        tasksRepository.save(existingTask);
    }

    @Override
    public void deleteTask(final Long id) {
        Tasks task = tasksRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException(
                TASK_NOT_FOUND + id));

        tasksRepository.delete(task);
    }

    @Override
    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }
}
