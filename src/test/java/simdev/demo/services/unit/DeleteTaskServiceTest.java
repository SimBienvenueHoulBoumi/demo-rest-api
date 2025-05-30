package simdev.demo.services.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import simdev.demo.exceptions.TaskNotFoundException;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksGetOneService;
import simdev.demo.servicesImpl.TasksDeleteServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTaskServiceTest {

    @Mock
    private TasksRepository tasksRepository;

    @Mock
    private TasksGetOneService tasksGetOneService;

    @InjectMocks
    private TasksDeleteServiceImpl tasksDeleteService;

    @Test
    void shouldDeleteTaskIfExists() {
        Long taskId = 1L;
        Tasks task = new Tasks();
        task.setId(taskId);

        when(tasksGetOneService.getTaskById(taskId)).thenReturn(task);

        tasksDeleteService.delete(taskId);

        verify(tasksRepository).delete(task);
    }

    @Test
    void shouldThrowIfTaskNotFound() {
        Long taskId = 1L;

        when(tasksGetOneService.getTaskById(taskId)).thenReturn(null);

        assertThatThrownBy(() -> tasksDeleteService.delete(taskId))
            .isInstanceOf(TaskNotFoundException.class)
            .hasMessageContaining("not found");

        verify(tasksRepository, never()).delete(any());
    }
}
