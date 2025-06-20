package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProjectRepository
 *
 * @summary
 * Repository interface for managing {@link Project} entities.
 * Provides standard CRUD operations and query capabilities via Spring Data JPA.
 *
 * This repository serves as the primary interface for accessing project data
 * from the underlying relational database.
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

}