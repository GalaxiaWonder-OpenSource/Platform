package com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectTeamMemberTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ProjectTeamMemberType
 *
 * @summary
 * Entity that represents a role or classification of a member within a project.
 * This class is backed by the {@link ProjectTeamMemberTypes} enum and defines a fixed set
 * of member types (e.g., COORDINATOR, SPECIALIST) used to assign responsibilities or permissions.
 *
 * <p>Examples: {@code COORDINATOR}, {@code SPECIALIST}</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTeamMemberType {
    /**
     * Database identifier for the project team member type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private ProjectTeamMemberTypes name;

    /**
     * Constructs an {@code ProjectTeamMemberType} with a specified {@link ProjectTeamMemberTypes} description.
     *
     * @param name the predefined member type classification
     */
    public ProjectTeamMemberType(ProjectTeamMemberTypes name) {
        this.name = name;
    }

    /**
     * Returns the default project team member type to be assigned when none is specified.
     *
     * @return an {@code ProjectTeamMemberType} instance with {@code COORDINATOR} as default
     */
    public static ProjectTeamMemberType getDefaultMemberType() {
        return new ProjectTeamMemberType(ProjectTeamMemberTypes.COORDINATOR);
    }

    /**
     * Converts a string representation of the member type projectName to an {@link ProjectTeamMemberType} instance.
     *
     * @param name the string description of the enum constant
     * @return a new {@code ProjectTeamMemberType} instance with the corresponding enum description
     * @throws IllegalArgumentException if the projectName does not match any enum constant
     */
    public static ProjectTeamMemberType toProjectTeamMemberTypeFromName(String name) {
        return new ProjectTeamMemberType(ProjectTeamMemberTypes.valueOf(name));
    }

    /**
     * Validates a list of member types. If null or empty, returns a list with the default type.
     *
     * @param types the list of member types to validate
     * @return the original list or a singleton list with the default type
     */
    public static List<ProjectTeamMemberType> validateProjectTeamMemberTypeSet(List<ProjectTeamMemberType> types) {
        return types == null || types.isEmpty()
                ? List.of(getDefaultMemberType())
                : types;
    }

    /**
     * Returns the string representation of the enum-based project team member type.
     *
     * @return the projectName of the project team member type as a string
     */
    public String getStringName() {
        return name.name();
    }
}
