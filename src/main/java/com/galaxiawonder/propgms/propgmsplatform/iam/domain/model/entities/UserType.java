package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.UserTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * UserType
 *
 * @summary
 * Entity that represents a **type of user** in the system, such as client or worker.
 * This class is backed by the {@link UserTypes} enum and defines a fixed classification
 * for users according to their functional identity within the platform.
 *
 * <p>Examples: {@code TYPE_CLIENT}, {@code TYPE_WORKER}</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserType {

    /** Database identifier for the user type */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    /** Enum projectName representing the user type classification */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserTypes name;

    /**
     * Constructs a UserType with a specified {@link UserTypes} description.
     *
     * @param name the predefined user type classification
     */
    public UserType(UserTypes name) {
        this.name = name;
    }

    /**
     * Returns the default user type to be assigned when no explicit type is provided.
     *
     * @return a {@code UserType} instance with {@code TYPE_CLIENT} as default
     */
    public static UserType getDefaultUserType() {
        return new UserType(UserTypes.TYPE_CLIENT);
    }

    /**
     * Converts a string representation of the user type projectName to a {@link UserType} instance.
     *
     * @param name the string description of the enum constant
     * @return a new {@code UserType} instance with the corresponding enum description
     * @throws IllegalArgumentException if the projectName does not match any enum constant
     */
    public static UserType toUserTypeFromName(String name) {
        return new UserType(UserTypes.valueOf(name));
    }

    /**
     * Validates a set of user types. If the input list is null or empty,
     * a list containing only the default user type is returned.
     *
     * @param types the list of user types to validate
     * @return the original list or a singleton list with the default user type
     */
    public static List<UserType> validateUserTypeSet(List<UserType> types) {
        return types == null || types.isEmpty()
                ? List.of(getDefaultUserType())
                : types;
    }

    /**
     * Returns the string representation of the enum-based user type.
     *
     * @return the projectName of the user type as a string
     */
    public String getStringName() {
        return name.name();
    }
}