package simdev.demo.services.integrate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import simdev.demo.exceptions.TaskNotFoundException;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksDeleteService;
import simdev.demo.services.TasksGetOneService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class DeleteTaskServiceTestIT {

    @Autowired
    private TasksDeleteService tasksDeleteService;

    @Autowired
    private TasksGetOneService tasksGetOneService;

    @Autowired
    private TasksRepository tasksRepository;

    @BeforeEach
    void cleanUp() {
        tasksRepository.deleteAll();
    }

    @Test
    void shouldDeleteTaskIfExists() {
        // Arrange : créer une tâche en base
        Tasks task = Tasks.builder()
                .name("Task to delete")
                .description("Will be deleted")
                .status("DONE")
                .build();
        Tasks saved = tasksRepository.save(task);

        // Vérifier que la tâche est bien en base avant suppression
        assertThat(tasksRepository.findById(saved.getId())).isPresent();

        // Act : supprimer la tâche
        tasksDeleteService.delete(saved.getId());

        // Assert : la tâche doit avoir disparu
        assertThat(tasksRepository.findById(saved.getId())).isNotPresent();
    }

    @Test
    void shouldThrowIfTaskNotFound() {
        Long nonExistingId = 999L;

        // Action + vérification : la suppression d'un id inexistant doit throw
        assertThatThrownBy(() -> tasksDeleteService.delete(nonExistingId))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("not found");
    }
}
