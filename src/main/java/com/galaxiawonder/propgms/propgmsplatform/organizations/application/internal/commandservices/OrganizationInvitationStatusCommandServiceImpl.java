package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationInvitationStatusCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationInvitationStatus;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationInvitationStatuses;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationInvitationStatusCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationInvitationStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * OrganizationInvitationStatusCommandService Implementation
 *
 * @summary
 * Service implementation responsible for handling organization invitation status command operations,
 * such as seeding default invitation statuses into the database if they do not exist.
 *
 * <p>Typical invitation statuses include: {@code PENDING}, {@code ACCEPTED}, {@code REJECTED}.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class OrganizationInvitationStatusCommandServiceImpl implements OrganizationInvitationStatusCommandService {

    private final OrganizationInvitationStatusRepository organizationInvitationStatusRepository;

    public OrganizationInvitationStatusCommandServiceImpl(OrganizationInvitationStatusRepository organizationInvitationStatusRepository) {
        this.organizationInvitationStatusRepository = organizationInvitationStatusRepository;
    }

    /**
     * Handles the seeding of organization invitation statuses.
     *
     * @param command the command that triggers the seeding process
     * @since 1.0
     */
    @Override
    public void handle(SeedOrganizationInvitationStatusCommand command) {
        Arrays.stream(OrganizationInvitationStatuses.values()).forEach(status -> {
            if (!organizationInvitationStatusRepository.existsByName(status)) {
                organizationInvitationStatusRepository.save(new OrganizationInvitationStatus(status));
            }
        });
    }
}

