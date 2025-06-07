package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.UserTypes;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

/**
 * Entity that represents a type of user in the system.
 * <p>
 * It is associated with the {@link UserTypes} enum and defines
 * the structure of the corresponding table in the database.
 * </p>
 *
 * <p>Examples: {@code TYPE_CLIENT}, {@code TYPE_WORKER}.</p>
 */
@Entity
public class UserType {

    /**
     * Unique identifier for the user type.
     * Automatically generated when persisted to the database.
     */
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the user type, based on the {@link UserTypes} enum.
     * Stored as a unique and non-null string.
     */
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private UserTypes name;

    /**
     * Default constructor required by JPA.
     */
    public UserType() {}

    /**
     * Creates a new {@code UserType} with the specified name.
     *
     * @param name the user type
     */
    public UserType(UserTypes name) {
        this.name = name;
    }

    /**
     * Returns the user type name as a string.
     *
     * @return the enum name as a string
     */
    public String getStringName() {
        return name.name();
    }

    /**
     * Returns the default user type ({@code TYPE_CLIENT}).
     *
     * @return an instance with the {@code TYPE_CLIENT} role
     */
    public static UserType getDefaultRole() {
        return new UserType(UserTypes.TYPE_CLIENT);
    }

    /**
     * Creates a {@code UserType} from the given enum name as a string.
     *
     * @param name the name of the user type (e.g., "TYPE_CLIENT")
     * @return a new {@code UserType} instance
     * @throws IllegalArgumentException if the name does not match a valid enum constant
     */
    public static UserType toRoleFromName(String name) {
        return new UserType(UserTypes.valueOf(name));
    }

    /**
     * Validates a list of user types.
     * If the list is null or empty, returns a list containing the default user type.
     *
     * @param userTypes the list of user types
     * @return a validated list with at least one user type
     */
    public static List<UserType> validateRoleSet(List<UserType> userTypes) {
        if (userTypes == null || userTypes.isEmpty()) {
            return List.of(getDefaultRole());
        }
        return userTypes;
    }
}