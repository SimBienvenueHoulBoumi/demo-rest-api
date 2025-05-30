package simdev.demo.services.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import simdev.demo.exceptions.TaskNotFoundException;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.servicesImpl.TasksGetOneServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTaskByIdServiceTest {

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksGetOneServiceImpl tasksService;

    @Test
    void shouldReturnTaskIfExists() {
        Tasks task = new Tasks();
        task.setId(1L);
        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));

        Tasks result = tasksService.getTaskById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        verify(tasksRepository).findById(1L);
    }

    @Test
    void shouldThrowIfTaskNotFound() {
        when(tasksRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tasksService.getTaskById(1L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("not found");

        verify(tasksRepository).findById(1L);
    }
}
