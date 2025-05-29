package simdev.demo.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // pour utiliser application-test.yml
class TasksIT {

    @Autowired
    private TasksService tasksService;

    @Autowired
    private TasksRepository tasksRepository;

    @BeforeEach
    void cleanUp() {
        tasksRepository.deleteAll(); // clean DB avant chaque test
    }

    @Test
    void shouldCreateAndFetchTask() {
        TasksDto dto = new TasksDto("Integration Task", "Test desc", "TODO");
        Tasks created = tasksService.createTask(dto);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("Integration Task");

        Tasks fetched = tasksService.getTaskById(created.getId());
        assertThat(fetched).isNotNull();
        assertThat(fetched.getName()).isEqualTo("Integration Task");
    }

    @Test
    void shouldUpdateTask() {
        TasksDto dto = new TasksDto("ToUpdate", "desc", "TODO");
        Tasks task = tasksService.createTask(dto);

        TasksDto updateDto = new TasksDto("Updated", "new desc", "DONE");
        tasksService.updateTask(task.getId(), updateDto);

        Tasks updated = tasksService.getTaskById(task.getId());
        assertThat(updated.getName()).isEqualTo("Updated");
        assertThat(updated.getStatus()).isEqualTo("DONE");
    }

    @Test
    void shouldDeleteTask() {
        TasksDto dto = new TasksDto("ToDelete", "desc", "TODO");
        Tasks task = tasksService.createTask(dto);

        tasksService.deleteTask(task.getId());

        assertThatThrownBy(() -> tasksService.getTaskById(task.getId()))
                .hasMessageContaining("Task not found");
    }

    @Test
    void shouldReturnAllTasks() {
        tasksService.createTask(new TasksDto("T1", "D1", "TODO"));
        tasksService.createTask(new TasksDto("T2", "D2", "DONE"));

        List<Tasks> allTasks = tasksService.getAllTasks();
        assertThat(allTasks).hasSize(2);
    }
}
