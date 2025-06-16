package simdev.demo.mapper;

import org.springframework.stereotype.Component;
import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;

@Component
public class TasksMapper {

    public Tasks toEntity(TasksDto dto) {
        Tasks task = new Tasks();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        return task;
    }

    public TasksDto toDto(Tasks task) {
        return new TasksDto(task.getName(), task.getDescription(), task.getStatus());
    }
}
