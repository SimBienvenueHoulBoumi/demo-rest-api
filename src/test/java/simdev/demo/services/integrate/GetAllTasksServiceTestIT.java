package simdev.demo.services.integrate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import simdev.demo.models.Tasks;
import simdev.demo.repositories.TasksRepository;
import simdev.demo.services.TasksGetAllService;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GetAllTasksServiceTestIT {

    @Autowired
    private TasksGetAllService tasksGetAllService;

    @Autowired
    private TasksRepository tasksRepository;

    @BeforeEach
    void cleanUp() {
        tasksRepository.deleteAll();
    }

    @Test
    void shouldReturnAllTasks() {
        // Arrange - insérer des tâches dans la base
        Tasks task1 = new Tasks(null, "Task1", "Desc1", "TODO", new Date(), new Date());
        Tasks task2 = new Tasks(null, "Task2", "Desc2", "DONE", new Date(), new Date());
        tasksRepository.save(task1);
        tasksRepository.save(task2);

        // Act - récupérer toutes les tâches via le service
        List<Tasks> result = tasksGetAllService.getAll();

        // Assert - on vérifie que tout est là, bien comme il faut
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Tasks::getName).containsExactlyInAnyOrder("Task1", "Task2");
    }
}
