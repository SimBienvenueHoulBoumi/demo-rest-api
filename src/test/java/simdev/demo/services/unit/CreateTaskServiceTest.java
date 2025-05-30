package simdev.demo.services.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import simdev.demo.dto.TasksDto;
import simdev.demo.exceptions.TaskAlreadyExistsException; // Updated import
import simdev.demo.mapper.TasksMapper;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.servicesImpl.TasksCreationServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskServiceTest {

    @Mock
    private TasksRepository tasksRepository;

    @Mock
    private TasksMapper tasksMapper;

    @InjectMocks
    private TasksCreationServiceImpl tasksService;

    @Test
    void shouldCreateNewTask() {
        TasksDto dto = new TasksDto("Task 1", "Description", "TODO");

        Tasks mappedTask = Tasks.builder()
                .name("Task 1")
                .description("Description")
                .status("TODO")
                // optionnel : createdAt, updatedAt, id, selon ta logique
                .build();

        when(tasksRepository.findByName("Task 1")).thenReturn(Optional.empty());
        when(tasksMapper.toEntity(dto)).thenReturn(mappedTask);
        when(tasksRepository.save(mappedTask)).thenReturn(mappedTask);

        Tasks savedTask = tasksService.create(dto);

        assertThat(savedTask.getName()).isEqualTo("Task 1");
        assertThat(savedTask.getDescription()).isEqualTo("Description");
        assertThat(savedTask.getStatus()).isEqualTo("TODO");

        verify(tasksRepository).save(mappedTask);
        verify(tasksMapper).toEntity(dto);
    }

    @Test
    void shouldThrowIfTaskAlreadyExists() {
        TasksDto dto = new TasksDto("Existing", "Desc", "TODO");

        Tasks existingTask = Tasks.builder()
                .name("Existing")
                .description("Desc")
                .status("TODO")
                .build();

        when(tasksRepository.findByName("Existing")).thenReturn(Optional.of(existingTask));

        assertThatThrownBy(() -> tasksService.create(dto))
                .isInstanceOf(TaskAlreadyExistsException.class)
                .hasMessageContaining("Task with name 'Existing' already exists");
    }
}