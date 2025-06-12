package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.SeedOrganizationMemberTypeCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.entities.OrganizationMemberType;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.OrganizationMemberTypes;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationMemberTypeCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationMemberTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * OrganizationMemberTypeCommandService Implementation
 *
 * @summary
 * Service implementation responsible for handling organization member type command operations,
 * such as seeding default organization member types into the database if they do not exist.
 *
 * <p>Typical member types include: {@code CONTRACTOR}, {@code WORKER}.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Service
public class OrganizationMemberTypeCommandServiceImpl implements OrganizationMemberTypeCommandService {

    private final OrganizationMemberTypeRepository organizationMemberTypeRepository;

    public OrganizationMemberTypeCommandServiceImpl(OrganizationMemberTypeRepository organizationMemberTypeRepository) {
        this.organizationMemberTypeRepository = organizationMemberTypeRepository;
    }

    /**
     * Handles the seeding of organization member types.
     *
     * @param command the command that triggers the seeding process
     * @since 1.0
     */
    @Override
    public void handle(SeedOrganizationMemberTypeCommand command) {
        Arrays.stream(OrganizationMemberTypes.values()).forEach(type -> {
            if (!organizationMemberTypeRepository.existsByName(type)) {
                organizationMemberTypeRepository.save(new OrganizationMemberType(type));
            }
        });
    }
}

