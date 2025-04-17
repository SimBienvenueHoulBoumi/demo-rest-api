package simdev.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;
import simdev.demo.services.TasksService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Endpoints for managing tasks")
public class TasksController {

    private final TasksService tasksService;

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<Tasks> createTask(@RequestBody TasksDto taskDto) {
        return ResponseEntity.ok(tasksService.createTask(taskDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a task by its ID")
    public ResponseEntity<Tasks> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(tasksService.getTaskById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task by its ID")
    public ResponseEntity<Void> updateTask(@PathVariable Long id, @RequestBody TasksDto taskDto) {
        tasksService.updateTask(id, taskDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by its ID")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        tasksService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all tasks")
    public ResponseEntity<List<Tasks>> getAllTasks() {
        return ResponseEntity.ok(tasksService.getAllTasks());
    }
}
