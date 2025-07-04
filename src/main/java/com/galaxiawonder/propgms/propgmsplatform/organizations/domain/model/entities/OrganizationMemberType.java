package com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationMemberTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * OrganizationMemberType
 *
 * @summary
 * Entity that represents a role or classification of a member within an organization.
 * This class is backed by the {@link OrganizationMemberTypes} enum and defines a fixed set
 * of member types (e.g., CONTRACTOR, WORKER) used to assign responsibilities or permissions.
 *
 * <p>Examples: {@code CONTRACTOR}, {@code WORKER}</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationMemberType {

    /**
     * Database identifier for the organization member type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Enum description representing the classification of the organization member type.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrganizationMemberTypes name;

    /**
     * Constructs an {@code OrganizationMemberType} with a specified {@link OrganizationMemberTypes} description.
     *
     * @param name the predefined member type classification
     */
    public OrganizationMemberType(OrganizationMemberTypes name) {
        this.name = name;
    }

    /**
     * Returns the default organization member type to be assigned when none is specified.
     *
     * @return an {@code OrganizationMemberType} instance with {@code WORKER} as default
     */
    public static OrganizationMemberType getDefaultMemberType() {
        return new OrganizationMemberType(OrganizationMemberTypes.WORKER);
    }

    /**
     * Converts a string representation of the member type projectName to an {@link OrganizationMemberType} instance.
     *
     * @param name the string description of the enum constant
     * @return a new {@code OrganizationMemberType} instance with the corresponding enum description
     * @throws IllegalArgumentException if the projectName does not match any enum constant
     */
    public static OrganizationMemberType toOrganizationMemberTypeFromName(String name) {
        return new OrganizationMemberType(OrganizationMemberTypes.valueOf(name));
    }

    /**
     * Validates a list of member types. If null or empty, returns a list with the default type.
     *
     * @param types the list of member types to validate
     * @return the original list or a singleton list with the default type
     */
    public static List<OrganizationMemberType> validateOrganizationMemberTypeSet(List<OrganizationMemberType> types) {
        return types == null || types.isEmpty()
                ? List.of(getDefaultMemberType())
                : types;
    }

    /**
     * Returns the string representation of the enum-based organization member type.
     *
     * @return the projectName of the organization member type as a string
     */
    public String getStringName() {
        return name.name();
    }
}