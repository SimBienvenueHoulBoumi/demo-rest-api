package simdev.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TasksDto {
  private String name;
  private String description;
  private String status;
}
