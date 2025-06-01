package com.galaxiawonder.propgms.propgmsplatform.organizations.infrastructure.persistence.jpa;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.Organization;
import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.valueobjects.Ruc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    /**
     * Check if an organization exists by ruc
     * @param ruc RUC
     * @return True if exists, false otherwise
     */
    boolean existsByRuc(Ruc ruc);
}
