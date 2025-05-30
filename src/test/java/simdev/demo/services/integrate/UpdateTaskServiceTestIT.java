package simdev.demo.services.integrate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksUpdateService;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UpdateTaskServiceTestIT {

    @Autowired
    private TasksUpdateService tasksUpdateService;

    @Autowired
    private TasksRepository tasksRepository;

    private Tasks existingTask;

    @BeforeEach
    void setup() {
        tasksRepository.deleteAll();
        existingTask = Tasks.builder()
                .name("Old")
                .description("Old Desc")
                .status("TODO")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        existingTask = tasksRepository.save(existingTask);
    }

    @Test
    void shouldUpdateTaskFields() {
        // Arrange : crée un DTO avec les nouvelles valeurs
        TasksDto dto = new TasksDto("New", "New Desc", "DONE");

        // Act : appelle la méthode update
        Tasks updatedTask = tasksUpdateService.update(existingTask.getId(), dto);

        // Assert : la tâche est mise à jour en base
        assertThat(updatedTask.getName()).isEqualTo("New");
        assertThat(updatedTask.getDescription()).isEqualTo("New Desc");
        assertThat(updatedTask.getStatus()).isEqualTo("DONE");

        // On peut aussi récupérer directement depuis le repo pour confirmer
        Tasks fromDb = tasksRepository.findById(existingTask.getId()).orElseThrow();
        assertThat(fromDb.getName()).isEqualTo("New");
        assertThat(fromDb.getDescription()).isEqualTo("New Desc");
        assertThat(fromDb.getStatus()).isEqualTo("DONE");
    }
}
