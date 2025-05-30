package simdev.demo.services.integrate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import simdev.demo.exceptions.TaskNotFoundException;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksGetOneService;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class GetTaskByIdServiceTestIT {

    @Autowired
    private TasksGetOneService tasksGetOneService;

    @Autowired
    private TasksRepository tasksRepository;

    @BeforeEach
    void setup() {
        tasksRepository.deleteAll();
    }

    @Test
    void shouldReturnTaskIfExists() {
        // Arrange : insère une tâche en base
        Tasks task = new Tasks(null, "Integration Task", "Test task description", "TODO", new Date(), new Date());
        Tasks saved = tasksRepository.save(task);

        // Act : récupère la tâche via le service
        Tasks found = tasksGetOneService.getTaskById(saved.getId());

        // Assert : vérifie que c’est bien la même tâche
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getName()).isEqualTo("Integration Task");
    }

    @Test
    void shouldThrowIfTaskNotFound() {
        // Aucun task en base, id 999 n’existe pas
        Long nonExistentId = 999L;

        // On vérifie que l’exception est bien levée
        assertThatThrownBy(() -> tasksGetOneService.getTaskById(nonExistentId))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("not found");
    }
}
