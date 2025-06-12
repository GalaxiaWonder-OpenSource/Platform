package com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    /**
     * Check if an organization exists by ruc
     * @param ruc RUC
     * @return True if exists, false otherwise
     */
    boolean existsByRuc(Ruc ruc);
    /**
     * Find an organization by ruc
     * @param ruc RUC
     * @return an Organization
     */
    Organization findByRuc(Ruc ruc);
}
