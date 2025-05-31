package com.galaxiawonder.propgms.propgmsplatform.infrastructure.persistence.jpa;

import com.galaxiawonder.propgms.propgmsplatform.organizations.domain.model.aggregates.OrganizationSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationSourceRepository extends JpaRepository<OrganizationSource, Long> {

}
