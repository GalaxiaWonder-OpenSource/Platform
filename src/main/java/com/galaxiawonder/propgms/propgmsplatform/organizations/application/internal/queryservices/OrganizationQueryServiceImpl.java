package com.galaxiawonder.propgms.propgmsplatform.organizations.application.internal.queryservices;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.services.OrganizationQueryService;
import com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * OrganizationQueryService Implementation
 *
 * @summary
 * Implementation of the OrganizationQueryService interface.
 * It is responsible for handling organization queries.
 */
@Service
public class OrganizationQueryServiceImpl implements OrganizationQueryService {
    private final OrganizationRepository organizationRepository;

    public OrganizationQueryServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Organization> handle(GetOrganizationByIdQuery query){
        return organizationRepository.findById(query.id());
    }
}
