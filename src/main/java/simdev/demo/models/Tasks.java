package simdev.demo.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a task entity in the demo REST API application.
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {
    /**
     * The unique identifier of the task.
     */
    private @Id @GeneratedValue Long id;

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The description of the task.
     */
    private String description;

    /**
     * The status of the task (e.g., pending, completed).
     */
    private String status;

    /**
     * The date and time when the task was created.
     */
    private Date createdAt;

    /**
     * The date and time when the task was last updated.
     */
    private Date updatedAt;

    /**
     * Sets the creation and update timestamps before persisting the task.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    /**
     * Updates the update timestamp before updating the task.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
