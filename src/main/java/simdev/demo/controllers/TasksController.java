package simdev.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import simdev.demo.dto.TasksDto;
import simdev.demo.models.Tasks;
import simdev.demo.services.TasksService;

import java.util.List;

/**
 * Controller REST pour gérer les opérations CRUD sur les tâches.
 *
 * <p>Ce contrôleur fournit les endpoints pour créer, lire,
 * mettre à jour et supprimer des tâches.</p>
 *
 * <p>Conçu pour être final afin d'éviter l'héritage non sécurisé.</p>
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Endpoints for managing tasks")
public final class TasksController {

    /**
     * Service chargé de la gestion des tâches.
     */
    private final TasksService tasksService;

    /**
     * Crée une nouvelle tâche.
     *
     * @param taskDto les données de la tâche à créer
     * @return la tâche créée
     */
    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<Tasks> createTask(
        @RequestBody final TasksDto taskDto
    ) {
        return ResponseEntity.ok(tasksService.createTask(taskDto));
    }

    /**
     * Récupère une tâche par son ID.
     *
     * @param id l'identifiant de la tâche
     * @return la tâche correspondante
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a task by its ID")
    public ResponseEntity<Tasks> getTaskById(
        @PathVariable final Long id
    ) {
        return ResponseEntity.ok(tasksService.getTaskById(id));
    }

    /**
     * Met à jour une tâche existante par son ID.
     *
     * @param id l'identifiant de la tâche à mettre à jour
     * @param taskDto les nouvelles données de la tâche
     * @return une réponse vide avec le statut no-content
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a task by its ID")
    public ResponseEntity<Void> updateTask(
        @PathVariable final Long id,
        @RequestBody final TasksDto taskDto
    ) {
        tasksService.updateTask(id, taskDto);
        return ResponseEntity
            .noContent()
            .build();
    }

    /**
     * Supprime une tâche par son ID.
     *
     * @param id l'identifiant de la tâche à supprimer
     * @return une réponse vide avec le statut no-content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by its ID")
    public ResponseEntity<Void> deleteTask(
        @PathVariable final Long id
    ) {
        tasksService.deleteTask(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    /**
     * Récupère la liste de toutes les tâches.
     *
     * @return la liste des tâches
     */
    @GetMapping
    @Operation(summary = "Get all tasks")
    public ResponseEntity<List<Tasks>> getAllTasks() {
        return ResponseEntity.ok(tasksService.getAllTasks());
    }
}
