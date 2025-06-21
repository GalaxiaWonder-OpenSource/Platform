package com.galaxiawonder.propgms.propgmsplatform.change.infrastructure.persistence.jpa.repositories;

import com.galaxiawonder.propgms.propgmsplatform.change.domain.model.aggregates.ChangeProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Change Process Repository
 *
 * @summary
 * JPA repository for managing {@link ChangeProcess} entities.
 * Provides methods for query change process
 *
 * @author
 * Galaxia Wonder Development Team
 * @since 1.0
 */
@Repository
public interface ChangeProcessRepository extends JpaRepository<ChangeProcess, Long> {
}
