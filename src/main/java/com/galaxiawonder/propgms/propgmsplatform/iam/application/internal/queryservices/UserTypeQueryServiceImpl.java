package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetAllUserTypesQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.queries.GetUserTypeByNameQuery;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.UserTypeQueryService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserTypeQueryService Implementation
 *
 * @summary
 * Service implementation responsible for handling queries related to user types,
 * such as retrieving all user types or fetching one by name.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class UserTypeQueryServiceImpl implements UserTypeQueryService {
    private final UserTypeRepository userTypeRepository;

    public UserTypeQueryServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    /**
     * Handles the query to retrieve all user types.
     *
     * @summary
     * Fetches all user types stored in the repository.
     *
     * @param query the query object (no parameters required)
     * @return a list of {@link UserType} entities
     * @since 1.0
     */
    @Override
    public List<UserType> handle(GetAllUserTypesQuery query) {
        return userTypeRepository.findAll();
    }

    /**
     * Handles the query to retrieve a user type by name.
     *
     * @summary
     * Finds a user type that matches the specified name, if it exists.
     *
     * @param query the query object containing the user type name to search for
     * @return an {@link Optional} containing the {@link UserType}, or empty if not found
     * @since 1.0
     */
    @Override
    public Optional<UserType> handle(GetUserTypeByNameQuery query) {
        return userTypeRepository.findByName(query.userType());
    }
}

