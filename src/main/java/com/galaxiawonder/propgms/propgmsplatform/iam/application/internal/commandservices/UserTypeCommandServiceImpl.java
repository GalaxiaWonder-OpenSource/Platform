package com.galaxiawonder.propgms.propgmsplatform.iam.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.commands.SeedUserTypeCommand;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.entities.UserType;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.model.valueobjects.UserTypes;
import com.galaxiawonder.propgms.propgmsplatform.iam.domain.services.UserTypeCommandService;
import com.galaxiawonder.propgms.propgmsplatform.iam.infrastructure.persitence.jpa.repositories.UserTypeRepository;
import jakarta.annotation.PostConstruct;
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
    private final UserTypeRepository userTypeRepository;

    public UserTypeCommandServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    /**
     * Handles the seeding of user types.
     *
     * @summary
     * Iterates through predefined user types and inserts them into the repository
     * if they are not already present.
     *
     * @param command the command that triggers the seeding process
     * @since 1.0
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

