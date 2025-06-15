package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SeedUserTypeCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.UserTypes;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.UserTypeCommandService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.UserTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * UserTypeCommandService Implementation
 *
 * @summary
 * Service implementation responsible for handling user type command operations,
 * such as seeding default user types into the database if they do not exist.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class UserTypeCommandServiceImpl implements UserTypeCommandService {

    /** Repository for managing {@link UserType} entities. */
    private final UserTypeRepository userTypeRepository;

    /**
     * Constructs a new {@code UserTypeCommandServiceImpl} with the specified repository.
     *
     * @param userTypeRepository the repository used to persist {@link UserType} entities
     */
    public UserTypeCommandServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(SeedUserTypeCommand command) {
        Arrays.stream(UserTypes.values()).forEach(userType -> {
            if (!userTypeRepository.existsByName(userType)) {
                userTypeRepository.save(new UserType(UserTypes.valueOf(userType.name())));
            }
        });
    }
}

