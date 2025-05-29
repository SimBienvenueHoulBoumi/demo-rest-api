package simdev.demo.exceptions;

/**
 * Exception thrown when a task is not found.
 */
public class TaskNotFoundException extends RuntimeException {

    /**
     * Constructs a new TaskNotFoundException with the specified message.
     * @param message the detail message
     */
    public TaskNotFoundException(final String message) {
        super(message);
    }
}
