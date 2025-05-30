package simdev.demo.mapper;

import org.springframework.stereotype.Component;
import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;

/**
 * Utility class for mapping between TasksDto and Tasks entities.
 */
@Component
public class TasksMapper {

    public Tasks toEntity(TasksDto dto) {
        if (dto == null) return null;
        return Tasks.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .build();
    }

    public TasksDto toDto(Tasks task) {
        if (task == null) return null;
        return new TasksDto(task.getName(), task.getDescription(), task.getStatus());
    }
}
