package com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.aggregates.UserAccount;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.UserName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserAccountRepository
 *
 * @summary
 * JPA repository interface for accessing {@link UserAccount} aggregates.
 * Provides query methods for checking the existence of accounts and retrieving them by username.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    /**
     * Checks if a {@link UserAccount} with the given {@link UserName} exists.
     *
     * @param userName the username to check
     * @return true if an account with the specified username exists; false otherwise
     *
     * @since 1.0
     */
    boolean existsByUserName(UserName userName);

    /**
     * Retrieves an account by its username.
     *
     * @param username the {@link UserName} of the account to retrieve
     * @return an {@link Optional} containing the matching {@link UserAccount}, or empty if not found
     *
     * @since 1.0
     */
    Optional<UserAccount> findByUserName(UserName username);
}

