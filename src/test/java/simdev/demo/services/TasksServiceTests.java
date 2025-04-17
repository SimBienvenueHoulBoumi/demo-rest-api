package simdev.demo.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import simdev.demo.dto.TasksDto;
import simdev.demo.exceptions.TaskNotFoundException;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.servicesImpl.TasksServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TasksServiceTest {

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private TasksServiceImpl tasksService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_shouldCreateNewTask() {
        TasksDto dto = new TasksDto("Task 1", "Description", "TODO");

        when(tasksRepository.getByName("Task 1")).thenReturn(null);
        when(tasksRepository.save(any(Tasks.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tasks savedTask = tasksService.createTask(dto);

        assertThat(savedTask.getName()).isEqualTo("Task 1");
        assertThat(savedTask.getStatus()).isEqualTo("TODO");
        assertThat(savedTask.getDescription()).isEqualTo("Description");
        assertThat(savedTask.getCreatedAt()).isNotNull();
        assertThat(savedTask.getUpdatedAt()).isNotNull();
        verify(tasksRepository).getByName("Task 1");
        verify(tasksRepository).save(any(Tasks.class));
    }

    @Test
    void createTask_shouldThrowIfTaskExists() {
        TasksDto dto = new TasksDto("Existing", "Desc", "TODO");

        when(tasksRepository.getByName("Existing")).thenReturn(new Tasks());

        assertThatThrownBy(() -> tasksService.createTask(dto))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void getTaskById_shouldReturnTask() {
        Tasks task = new Tasks();
        task.setId(1L);
        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));

        Tasks result = tasksService.getTaskById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getTaskById_shouldThrowIfNotFound() {
        when(tasksRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tasksService.getTaskById(1L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void updateTask_shouldUpdateFields() {
        Tasks existing = Tasks.builder()
                .id(1L)
                .name("Old")
                .description("Old Desc")
                .status("TODO")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        TasksDto dto = new TasksDto("New", "New Desc", "DONE");

        when(tasksRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(tasksRepository.save(any(Tasks.class))).thenAnswer(invocation -> invocation.getArgument(0));

        tasksService.updateTask(1L, dto);

        assertThat(existing.getName()).isEqualTo("New");
        assertThat(existing.getStatus()).isEqualTo("DONE");
        verify(tasksRepository).save(existing);
    }

    @Test
    void deleteTask_shouldRemoveTask() {
        Tasks task = new Tasks();
        task.setId(1L);
        when(tasksRepository.findById(1L)).thenReturn(Optional.of(task));

        tasksService.deleteTask(1L);

        verify(tasksRepository).delete(task);
    }

    @Test
    void getAllTasks_shouldReturnAllTasks() {
        List<Tasks> list = List.of(
                new Tasks(1L, "Task1", "Desc1", "TODO", new Date(), new Date()),
                new Tasks(2L, "Task2", "Desc2", "DONE", new Date(), new Date())
        );
        when(tasksRepository.findAll()).thenReturn(list);

        List<Tasks> result = tasksService.getAllTasks();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Task1");
    }
}

