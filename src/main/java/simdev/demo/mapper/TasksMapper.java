package simdev.demo.mapper;

import java.time.Instant;
import java.util.Date;

import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;

/**
 * Utility class for mapping between TasksDto and Tasks entities.
 */
public final class TasksMapper {

    private TasksMapper() {
        // Prevent instantiation
    }

    /**
     * Converts a TasksDto to a Tasks entity.
     * @param dto the TasksDto to convert
     * @return the converted Tasks entity, or null if the input is null
     */
    public static Tasks toEntity(final TasksDto dto) {
        if (dto == null) {
            return null;
        }

        Instant now = Instant.now();

        return Tasks.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .createdAt(Date.from(now))
                .updatedAt(Date.from(now))
                .build();
    }

    /**
     * Converts a Tasks entity to a TasksDto.
     * @param entity the Tasks entity to convert
     * @return the converted TasksDto, or null if the input is null
     */
    public static TasksDto toDto(final Tasks entity) {
        if (entity == null) {
            return null;
        }

        return TasksDto.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .build();
    }
}
