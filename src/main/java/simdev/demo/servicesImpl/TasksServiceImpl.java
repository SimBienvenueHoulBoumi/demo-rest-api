package simdev.demo.servicesImpl;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import simdev.demo.dto.TasksDto;
import simdev.demo.exceptions.TaskNotFoundException;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksService;
import simdev.demo.mapper.TasksMapper;

@Service
@AllArgsConstructor
public class TasksServiceImpl implements TasksService {

    private final TasksRepository tasksRepository;

    @Override
    public Tasks createTask(TasksDto newTask) {
        if (tasksRepository.getByName(newTask.getName()) != null) {
            throw new TaskNotFoundException("Task already exists with name: " + newTask.getName());
        }

        Tasks task = TasksMapper.toEntity(newTask);
        return tasksRepository.save(task);
    }

    @Override
    public Tasks getTaskById(Long id) {
        return tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }

    @Override
    public void updateTask(Long id, TasksDto taskDto) {
        Tasks existingTask = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        existingTask.setName(taskDto.getName());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(taskDto.getStatus());
        existingTask.setUpdatedAt(Date.from(Instant.now()));

        tasksRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        Tasks task = tasksRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));

        tasksRepository.delete(task);
    }

    @Override
    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }
}
