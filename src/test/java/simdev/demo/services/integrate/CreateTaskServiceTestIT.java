package simdev.demo.services.integrate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import simdev.demo.dto.TasksDto;
import simdev.demo.exceptions.TaskAlreadyExistsException;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksCreationService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CreateTaskServiceTestIT {

    @Autowired
    private TasksCreationService tasksCreationService;

    @Autowired
    private TasksRepository tasksRepository;

    @BeforeEach
    void cleanDatabase() {
        tasksRepository.deleteAll();
    }

    @Test
    void shouldCreateNewTask() {
        TasksDto dto = new TasksDto("New Task", "Integration test task", "TODO");

        Tasks created = tasksCreationService.create(dto);

        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("New Task");
        assertThat(created.getDescription()).isEqualTo("Integration test task");
        assertThat(created.getStatus()).isEqualTo("TODO");

        // Double check in DB
        assertThat(tasksRepository.findById(created.getId())).isPresent();
    }

    @Test
    void shouldThrowIfTaskAlreadyExists() {
        // Arrange: create task first
        TasksDto dto = new TasksDto("Duplicate Task", "Desc", "TODO");
        tasksCreationService.create(dto);

        // Act & Assert: creating same task again throws exception
        assertThatThrownBy(() -> tasksCreationService.create(dto))
            .isInstanceOf(TaskAlreadyExistsException.class)
            .hasMessageContaining("Task with name 'Duplicate Task' already exists");
    }
}
