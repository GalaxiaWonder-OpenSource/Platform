package com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.Password;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.PersonId;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.UserName;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * UserAccount
 *
 * @summary
 * Aggregate root representing a user account in the system.
 * Encapsulates identity, authentication credentials and role type.
 * Linked to a corresponding {@link Person} through an embedded identifier.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Entity
public class UserAccount extends AuditableAbstractAggregateRoot<UserAccount> {

    /** Unique username for account authentication, represented as a value object */
    @Getter
    @Embedded
    private UserName userName;

    /** Securely hashed password for authentication */
    @Getter
    @Embedded
    private Password hashedPassword;

    /** User role or category (e.g., CLIENT, WORKER), persisted as a separate entity */
    @Getter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_type_id", nullable = false, unique = false)
    private UserType userType;

    /** Link to the associated person entity */
    @Getter
    @Embedded
    private PersonId personId;

    /**
     * Default constructor required by JPA.
     */
    public UserAccount() {}

    /**
     * Constructs a UserAccount with username, hashed password and user type.
     *
     * @param userName  the plain username to be wrapped in a value object
     * @param password  the hashed password (must be pre-encoded)
     * @param userType  the role type assigned to the user
     */
    public UserAccount(String userName, String password, UserType userType) {
        this.userName = new UserName(userName);
        this.hashedPassword = new Password(password);
        this.userType = userType;
    }

    /**
     * Assigns the associated person identifier to this account.
     *
     * @param id the person ID to link, usually obtained after creating the person entity
     */
    public void assignPersonId(Long id) {
        this.personId = new PersonId(id);
    }

    /**
     * Changes the current password to a new one.
     * The password should be pre-encoded before being passed.
     *
     * @param password the new hashed password
     */
    public void changePassword(String password) {
        this.hashedPassword = new Password(password);
    }
}
