package com.galaxiawonder.propgms.propgmsplatform.projects.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.projects.domain.model.aggregates.ProjectTeamMember;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.OrganizationMemberId;
import com.galaxiawonder.propgms.propgmsplatform.shared.domain.model.valueobjects.ProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link ProjectTeamMember} entities.
 *
 * <p>This interface extends {@link JpaRepository}, providing standard data access methods such as
 * save, delete, and findById for managing project team members within the database.</p>
 *
 * <p>It may also be extended with custom query methods to support specific use cases
 * related to project team composition and assignment.</p>
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
public interface ProjectTeamMemberRepository extends JpaRepository<ProjectTeamMember, Long> {
    Optional<ProjectTeamMember> findByOrganizationMemberIdAndProjectId(OrganizationMemberId organizationMemberId, ProjectId projectId);
    Optional<List<ProjectTeamMember>> findAllByProjectId(ProjectId projectId);
}

