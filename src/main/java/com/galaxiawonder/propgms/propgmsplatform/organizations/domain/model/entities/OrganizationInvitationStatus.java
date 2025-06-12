package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationInvitationStatuses;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OrganizationInvitationStatus
 *
 * @summary
 * Entity that represents the status of an organization invitation.
 * This class is backed by the {@link OrganizationInvitationStatuses} enum and defines
 * a fixed set of invitation states used throughout the organization onboarding process.
 *
 * <p>Examples: {@code PENDING}, {@code ACCEPTED}, {@code REJECTED}</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationInvitationStatus {

    /**
     * Database identifier for the organization invitation status.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Enum value representing the classification of the invitation status.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrganizationInvitationStatuses name;

    /**
     * Constructs an {@code OrganizationInvitationStatus} with a specified {@link OrganizationInvitationStatuses} value.
     *
     * @param name the predefined invitation status
     */
    public OrganizationInvitationStatus(OrganizationInvitationStatuses name) {
        this.name = name;
    }

    /**
     * Returns the default invitation status to be assigned when none is specified.
     *
     * @return an {@code OrganizationInvitationStatus} instance with {@code PENDING} as default
     */
    public static OrganizationInvitationStatus getDefaultStatus() {
        return new OrganizationInvitationStatus(OrganizationInvitationStatuses.PENDING);
    }

    /**
     * Converts a string representation of the invitation status name to an {@link OrganizationInvitationStatus} instance.
     *
     * @param name the string value of the enum constant
     * @return a new {@code OrganizationInvitationStatus} instance with the corresponding enum value
     * @throws IllegalArgumentException if the name does not match any enum constant
     */
    public static OrganizationInvitationStatus toOrganizationInvitationStatusFromName(String name) {
        return new OrganizationInvitationStatus(OrganizationInvitationStatuses.valueOf(name));
    }

    /**
     * Validates a list of invitation statuses. If null or empty, returns a list with the default status.
     *
     * @param statuses the list of invitation statuses to validate
     * @return the original list or a singleton list with the default status
     */
    public static List<OrganizationInvitationStatus> validateStatusSet(List<OrganizationInvitationStatus> statuses) {
        return statuses == null || statuses.isEmpty()
                ? List.of(getDefaultStatus())
                : statuses;
    }

    /**
     * Returns the string representation of the enum-based invitation status.
     *
     * @return the name of the invitation status as a string
     */
    public String getStringName() {
        return name.name();
    }
}

