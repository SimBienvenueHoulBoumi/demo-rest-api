package simdev.demo.services.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.servicesImpl.TasksGetAllServiceImpl;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllTasksServiceTest {

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksGetAllServiceImpl tasksGetAllService;

    @Test
    void shouldReturnAllTasks() {
        List<Tasks> list = List.of(
                new Tasks(1L, "Task1", "Desc1", "TODO", new Date(), new Date()),
                new Tasks(2L, "Task2", "Desc2", "DONE", new Date(), new Date())
        );
        when(tasksRepository.findAll()).thenReturn(list);

        List<Tasks> result = tasksGetAllService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Task1");

        verify(tasksRepository, times(1)).findAll();
    }
}
