package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationStatuses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OrganizationStatus
 *
 * @summary
 * Entity that represents the operational status of an organization in the system.
 * This class is backed by the {@link OrganizationStatuses} enum and defines a fixed set
 * of statuses (e.g., ACTIVE, INACTIVE) used to manage organization lifecycle states.
 *
 * <p>Examples: {@code ACTIVE}, {@code INACTIVE}</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationStatus {

    /**
     * Database identifier for the organization status.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    /**
     * Enum description representing the classification of the organization's status.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrganizationStatuses name;

    /**
     * Constructs an {@code OrganizationStatus} with a specified {@link OrganizationStatuses} description.
     *
     * @param name the predefined organization status classification
     */
    public OrganizationStatus(OrganizationStatuses name) {
        this.name = name;
    }

    /**
     * Returns the default organization status to be assigned when no explicit status is provided.
     *
     * @return an {@code OrganizationStatus} instance with {@code ACTIVE} as default
     */
    public static OrganizationStatus getDefaultUserType() {
        return new OrganizationStatus(OrganizationStatuses.ACTIVE);
    }

    /**
     * Converts a string representation of the organization status projectName to an {@link OrganizationStatus} instance.
     *
     * @param name the string description of the enum constant
     * @return a new {@code OrganizationStatus} instance with the corresponding enum description
     * @throws IllegalArgumentException if the projectName does not match any enum constant
     */
    public static OrganizationStatus toOrganizationStatusFromName(String name) {
        return new OrganizationStatus(OrganizationStatuses.valueOf(name));
    }

    /**
     * Validates a set of organization statuses. If the input list is null or empty,
     * a list containing only the default organization status is returned.
     *
     * @param types the list of organization statuses to validate
     * @return the original list or a singleton list with the default status
     */
    public static List<OrganizationStatus> validateOrganizationStatusSet(List<OrganizationStatus> types) {
        return types == null || types.isEmpty()
                ? List.of(getDefaultUserType())
                : types;
    }

    /**
     * Returns the string representation of the enum-based organization status.
     *
     * @return the projectName of the organization status as a string
     */
    public String getStringName() {
        return name.name();
    }
}