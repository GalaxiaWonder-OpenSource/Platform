package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationStatuses;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationStatusCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationStatusRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * OrganizationStatusCommandService Implementation
 *
 * @summary
 * Service implementation responsible for handling organization status command operations,
 * such as seeding default organization statuses into the database if they do not exist.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class OrganizationStatusCommandServiceImpl implements OrganizationStatusCommandService {
    /** Repository for managing {@link OrganizationStatus} entities. */
    private final OrganizationStatusRepository organizationStatusRepository;

    /**
     * Constructs a new {@code OrganizationStatusCommandServiceImpl} with the specified repository.
     *
     * @param organizationStatusRepository the repository used to persist organization status values
     */
    public OrganizationStatusCommandServiceImpl(OrganizationStatusRepository organizationStatusRepository) {
        this.organizationStatusRepository = organizationStatusRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(SeedOrganizationStatusCommand command) {
        Arrays.stream(OrganizationStatuses.values()).forEach(status -> {
            if (!organizationStatusRepository.existsByName(status)) {
                organizationStatusRepository.save(new OrganizationStatus(status));
            }
        });
    }
}
