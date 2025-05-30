package simdev.demo.servicesImpl;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksGetAllService;

@Service
@AllArgsConstructor
public class TasksGetAllServiceImpl implements TasksGetAllService {

    private final TasksRepository tasksRepository;

    @Override
    public List<Tasks> getAll() {
        return tasksRepository.findAll();
    }
}
