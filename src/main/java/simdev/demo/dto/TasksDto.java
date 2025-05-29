package simdev.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object for tasks.
 */
@Data
@Builder
@AllArgsConstructor
public class TasksDto {
    /** The name of the task. */
    private String name;

    /** The description of the task. */
    private String description;

    /** The status of the task. */
    private String status;
}
