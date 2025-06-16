package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.TaskStatuses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TaskStatus
 *
 * @summary
 * Entity that represents the status of a task.
 * This class is backed by the {@link TaskStatuses} enum and defines
 * a fixed set of task lifecycle states.
 *
 * <p>Examples: {@code DRAFT}, {@code SUBMITTED}, {@code APPROVED}</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatus {

    /**
     * Database identifier for the task status.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Enum value representing the task status.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private TaskStatuses name;

    /**
     * Constructs a {@code TaskStatus} with the given {@link TaskStatuses}.
     *
     * @param name the enum value representing the task status
     */
    public TaskStatus(TaskStatuses name) {
        this.name = name;
    }

    /**
     * Returns the default task status to be assigned when none is specified.
     *
     * @return a {@code TaskStatus} instance with {@code DRAFT} as default
     */
    public static TaskStatus getDefaultStatus() {
        return new TaskStatus(TaskStatuses.DRAFT);
    }

    /**
     * Converts a string to a {@link TaskStatus} using the corresponding enum value.
     *
     * @param name the string representation of the enum constant
     * @return a new {@code TaskStatus} instance with the corresponding enum value
     * @throws IllegalArgumentException if the name does not match any enum constant
     */
    public static TaskStatus toTaskStatusFromName(String name) {
        return new TaskStatus(TaskStatuses.valueOf(name));
    }

    /**
     * Validates a list of task statuses. If null or empty, returns a list with the default status.
     *
     * @param statuses the list of task statuses to validate
     * @return the original list or a singleton list with the default status
     */
    public static List<TaskStatus> validateStatusSet(List<TaskStatus> statuses) {
        return statuses == null || statuses.isEmpty()
                ? List.of(getDefaultStatus())
                : statuses;
    }

    /**
     * Returns the string representation of the enum-based task status.
     *
     * @return the name of the task status as a string
     */
    public String getStringName() {
        return name.name();
    }
}
