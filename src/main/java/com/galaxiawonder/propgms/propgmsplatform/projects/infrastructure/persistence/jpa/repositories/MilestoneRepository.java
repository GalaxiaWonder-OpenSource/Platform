package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Milestone;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MilestoneRepository
 *
 * @summary
 * Repository interface for managing {@link Milestone} entities.
 * Provides standard CRUD operations and query capabilities via Spring Data JPA.
 * This repository serves as the primary interface for accessing project data
 * from the underlying relational database.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
