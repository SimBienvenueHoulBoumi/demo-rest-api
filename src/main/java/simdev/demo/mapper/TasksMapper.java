package simdev.demo.mapper;


import java.time.Instant;
import java.util.Date;

import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;

public class TasksMapper {

    public static Tasks toEntity(TasksDto dto) {
        if (dto == null) return null;

        Instant now = Instant.now();

        return Tasks.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .createdAt(Date.from(now))
                .updatedAt(Date.from(now))
                .build();
    }

    public static TasksDto toDto(Tasks entity) {
        if (entity == null) return null;

        return TasksDto.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }
}
