package simdev.demo.servicesImpl;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import simdev.demo.exceptions.TaskAlreadyExistsException;
import simdev.demo.mapper.TasksMapper;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksCreationService;
import simdev.demo.dto.TasksDto;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TasksCreationServiceImpl implements TasksCreationService {

    private final TasksRepository tasksRepository;
    private final TasksMapper tasksMapper;

   @Override
    public Tasks create(TasksDto newTask) {
        // Vérifier si la tâche existe déjà
        Optional<Tasks> existingTask = tasksRepository.findByName(newTask.getName());
        if (existingTask.isPresent()) {
            throw new TaskAlreadyExistsException("Task with name '" + newTask.getName() + "' already exists");
        }
        // Mapper DTO vers entity via l'instance injectée
        Tasks taskEntity = tasksMapper.toEntity(newTask);
        // Sauvegarder en base
        return tasksRepository.save(taskEntity);
    }
}