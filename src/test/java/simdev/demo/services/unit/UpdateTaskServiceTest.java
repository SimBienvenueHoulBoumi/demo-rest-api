package simdev.demo.services.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito; // Added import for Mockito
import org.mockito.junit.jupiter.MockitoExtension;
import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksGetOneService;
import simdev.demo.servicesImpl.TasksUpdateServiceImpl;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateTaskServiceTest {

    @Mock
    private TasksRepository tasksRepository;

    @Mock
    private TasksGetOneService tasksGetOneService;

    @InjectMocks
    private TasksUpdateServiceImpl tasksUpdateService;

    @Test
    void shouldUpdateTaskFields() {
        // Arrange: la tâche avant update
        Tasks existing = Tasks.builder()
                .id(1L)
                .name("Old")
                .description("Old Desc")
                .status("TODO")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        TasksDto dto = new TasksDto("New", "New Desc", "DONE");

        when(tasksGetOneService.getTaskById(1L)).thenReturn(existing);
        when(tasksRepository.save(any(Tasks.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Tasks updatedTask = tasksUpdateService.update(1L, dto);

        // Assert
        assertThat(updatedTask.getName()).isEqualTo("New");
        assertThat(updatedTask.getDescription()).isEqualTo("New Desc");
        assertThat(updatedTask.getStatus()).isEqualTo("DONE");

        verify(tasksGetOneService).getTaskById(1L);
        verify(tasksRepository).save(existing);
    }
}