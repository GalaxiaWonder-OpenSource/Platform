package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.commandservices;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.CreateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.DeleteOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationCommandService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.OrganizationRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * OrganizationCommandService Implementation
 *
 * @summary
 * Implementation of the OrganizationCommandService interface.
 * It is responsible for handling organization commands.
 */
@Service
public class OrganizationCommandServiceImpl implements OrganizationCommandService {
    private final OrganizationRepository organizationRepository;
    public OrganizationCommandServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Organization> handle(CreateOrganizationCommand command){
        if(organizationRepository.existsByRuc(new Ruc(command.ruc())))
            throw new IllegalArgumentException("Organization with same RUC already exists for this API key");
        var organization = new Organization(command);
        var createdOrganization = organizationRepository.save(organization);
        return Optional.of(createdOrganization);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(DeleteOrganizationCommand command) {
        Ruc ruc = new Ruc(command.ruc());
        if (!organizationRepository.existsByRuc(ruc)) {
            throw new IllegalArgumentException("Organization doesn't exist");
        }
        Organization organization = organizationRepository.findByRuc(ruc);
        organizationRepository.delete(organization);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Organization> handle(UpdateOrganizationCommand command) {
        var result = organizationRepository.findById(command.organizationId());
        if (result.isEmpty())
            throw new IllegalArgumentException("Organization doesn't exist");
        var organizationToUpdate = result.get();
        try{
            var updatedOrganization = organizationRepository.save(organizationToUpdate.updateInformation(command.commercialName()));
            return Optional.of(updatedOrganization);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating organization: %s".formatted(e.getMessage()));
        }
    }
}
