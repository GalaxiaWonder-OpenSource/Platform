package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.entities.ProjectTeamMemberType;
import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.valueobjects.ProjectTeamMemberTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link ProjectTeamMemberType} entities.
 *
 * <p>This repository is responsible for managing predefined project team member roles,
 * such as {@code COORDINATOR} and {@code SPECIALIST}.</p>
 *
 * <p>It supports standard CRUD operations and also provides custom query methods
 * for looking up roles by their enum value.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface ProjectTeamMemberTypeRepository extends JpaRepository<ProjectTeamMemberType, Long> {

    Optional<ProjectTeamMemberType> findByName(ProjectTeamMemberTypes name);

    boolean existsByName(ProjectTeamMemberTypes name);
}